package com.barman.barman.commands;

import com.barman.barman.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.camera.ICameraService;
import com.barman.barman.dao.IDrinksDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.Drink;
import com.barman.barman.gpio.PumpRunner;
import com.barman.barman.gpio.pump.IPumpService;

public class PumpDrinkCommand extends AbstractCommandProcessor
{

    private static final Logger LOG = LoggerFactory.getLogger(PumpDrinkCommand.class);

    @Autowired
    private IDrinksDao drinksDao;

    @Autowired
    private IPumpService pumpService;

    @Autowired
    private ICameraService cameraService;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace)
    {
        Drink drink = findDrink(cspace.getRequestMessage());

        if(drink == null)
        {
            cspace.setResponseMessage("могу предложить: \n" + getDrinksList());
        }
        else if(cspace.isCupClose())
        {
            if(Constants.N.equals(cspace.getUserPrivilege().getAllowDrink())) {
                cspace.setRequestMessage(Constants.ACCESS_DENIED);
                return cspace;
            }

            String response = "готовлю " + cspace.getRequestMessage() + " для " + cspace.getMessage().getFrom().getFirstName();
            cspace.setResponseMessage(response);
            cameraService.takePicture();
            runPumps(drink);
        }

        return cspace;
    }

    private Drink findDrink(String name)
    {
        Iterable<Drink> drinks = drinksDao.findAll();

        for(Drink drink : drinks)
        {
            if(drink.getName().equalsIgnoreCase(name))
            {
                return drink;
            }
        }

        return null;
    }

    private void runPumps(Drink drink)
    {
        LOG.info("Run pumps");
        PumpRunner pumpOne = new PumpRunner(pumpService.getPumpOne(),drink.getPumpOne());
        PumpRunner pumpTwo = new PumpRunner(pumpService.getPumpTwo(),drink.getPumpTwo());
        PumpRunner pumpThree = new PumpRunner(pumpService.getPumpThree(),drink.getPumpThree());

        pumpOne.start();
        pumpTwo.start();
        pumpThree.start();
    }

    private String getDrinksList()
    {
        Iterable<Drink> drinks = drinksDao.findAll();
        StringBuilder sb = new StringBuilder();

        for(Drink drink : drinks)
        {
            sb = sb.append(drink.getId() + ": " + drink.getName()).append("\n");
        }

        return sb.toString();
    }
}
