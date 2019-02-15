package com.barman.barman.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.barman.barman.gpio.IDistanceService;
import com.barman.barman.gpio.IDistanceStateHolder;

@Component
public class DistanceCheckSchedule {

	@Autowired
	private IDistanceService distanceService;
	
	@Autowired
	private IDistanceStateHolder state;
	
	//@Scheduled(fixedRate = 100)
	public void checkDistance() throws InterruptedException
	{
		state.setTime(System.nanoTime());
        distanceService.getTrigerPin().toggle();
        Thread.sleep(10);
        distanceService.getTrigerPin().toggle();
	}
}
