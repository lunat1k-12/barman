package com.barman.barman.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;

import com.barman.barman.dao.IDrinksDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.Drink;

public class NewDrinkCommand extends AbstractCommandProcessor
{

    public static final String NEW_DRINK_COMMAND = "drink:";

    public static final String ADMIN_NAME = "Evgeniy/Chemeris";

    @Autowired
    private IDrinksDao drinksDao;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace)
    {
        Message message = cspace.getMessage();

        if(cspace.getRequestMessage() != null && cspace.getRequestMessage().startsWith(NEW_DRINK_COMMAND))
        {

            String user = message.getFrom().getFirstName() + "/" + message.getFrom().getLastName();
            String response = resolveDrinkCommand(message.getText(),user);
            cspace.setResponseMessage(response);
            return cspace;
        }

        return this.processNextOrExit(cspace);
    }

    private String resolveDrinkCommand(String text, String user)
    {
        String[] attributes = text.substring(6,text.length()).split(",");
        String res = "у тебя нет прав";

        if(!ADMIN_NAME.equals(user))
        {
            return res;
        }

        if("delete".equalsIgnoreCase(attributes[0]))
        {
            Drink drink = drinksDao.findByName(attributes[1]);
            if(drink == null)
            {
                res = "нет такого меню";
            }
            else
            {
                drinksDao.delete(drink);
                res = "удален.";
            }
        }
        else
        {
            Drink drink = new Drink();
            drink.setName(attributes[0]);
            drink.setPumpOne(Long.parseLong(attributes[1]));
            drink.setPumpTwo(Long.parseLong(attributes[2]));
            drink.setPumpThree(Long.parseLong(attributes[3]));
            drinksDao.save(drink);
            res = "рецепт добавлен";
        }

        return res;
    }
}
