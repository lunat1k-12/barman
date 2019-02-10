package com.barman.barman.commands;

import com.barman.barman.domain.CommandWorkspace;

public interface ICommandProcessor
{

    CommandWorkspace processCommand(CommandWorkspace cspace);

    ICommandProcessor getNext();

    ICommandProcessor setNext(ICommandProcessor cspace);

    default CommandWorkspace processNextOrExit(CommandWorkspace cspace)
    {
        return getNext() == null ? cspace : getNext().processCommand(cspace);
    }
}
