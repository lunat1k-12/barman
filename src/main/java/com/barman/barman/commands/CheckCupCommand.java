package com.barman.barman.commands;

import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.gpio.IDistanceStateHolder;

public class CheckCupCommand extends AbstractCommandProcessor {

	@Autowired
	private IDistanceStateHolder state;

	@Override
	public CommandWorkspace processCommand(CommandWorkspace cspace) {
	    cspace.setCupClose(state.isClose());
		if(!state.isClose())
		{
			cspace.setResponseMessage("подставь бокал !!!");
		}

		return this.processNextOrExit(cspace);
	}

}
