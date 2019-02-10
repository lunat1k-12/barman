package com.barman.barman.commands.message;

import org.telegram.telegrambots.api.objects.Message;

import com.barman.barman.domain.CommandWorkspace;

public interface IMessageProcessor
{

    CommandWorkspace processMessage(Message message);
}
