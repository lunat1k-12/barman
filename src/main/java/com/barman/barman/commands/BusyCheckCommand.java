package com.barman.barman.commands;

import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.gpio.pump.IPumpService;

public class BusyCheckCommand extends AbstractCommandProcessor
{

    @Autowired
    private IPumpService pumpService;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace)
    {

        if(pumpService.isBusy())
        {
            String response = cspace.getMessage().getFrom().getFirstName() + ", я ЗАНЯТ !!!!";
            cspace.setResponseMessage(response);
            return cspace;
        }

        return this.processNextOrExit(cspace);
    }

}
