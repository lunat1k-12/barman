package com.barman.barman.commands.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Message;

import com.barman.barman.commands.ICommandProcessor;
import com.barman.barman.domain.CommandWorkspace;

public class MessageProcessor implements IMessageProcessor
{

    @Autowired
    private ICommandProcessor command;

    @Override
    public CommandWorkspace processMessage(Message message)
    {
        CommandWorkspace cspace = new CommandWorkspace();
        cspace.setMessage(message);
        cspace.setRequestMessage(message.getText().trim());

        return command.processCommand(cspace);
    }

}
