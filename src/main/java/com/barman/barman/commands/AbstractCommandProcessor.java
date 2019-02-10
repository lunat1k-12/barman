package com.barman.barman.commands;

public abstract class AbstractCommandProcessor implements ICommandProcessor
{

    private ICommandProcessor next;

    @Override
    public ICommandProcessor getNext()
    {
        return next;
    }

    @Override
    public ICommandProcessor setNext(ICommandProcessor cspace)
    {
        this.next = cspace;
        return next;
    }

}
