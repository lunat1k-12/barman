package com.barman.barman.schedule;

import com.barman.barman.schedule.service.IRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.barman.barman.gpio.IDistanceService;
import com.barman.barman.gpio.IDistanceStateHolder;

@Component
public class AppSchedule {

	@Autowired
	private IDistanceService distanceService;
	
	@Autowired
	private IDistanceStateHolder state;

	@Autowired
	private IRecognitionService recService;
	
	//@Scheduled(fixedRate = 100)
	public void checkDistance() throws InterruptedException
	{
		state.setTime(System.nanoTime());
        distanceService.getTriggerPin().toggle();
        Thread.sleep(10);
        distanceService.getTriggerPin().toggle();
	}

	@Scheduled(fixedRate = 180_000)
	public void checkPersons() {
		recService.checkPerson();
	}
}
