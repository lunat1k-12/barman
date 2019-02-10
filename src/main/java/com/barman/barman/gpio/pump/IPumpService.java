package com.barman.barman.gpio.pump;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public interface IPumpService {

	GpioPinDigitalOutput getPumpOne();

	GpioPinDigitalOutput getPumpTwo();

	GpioPinDigitalOutput getPumpThree();
	
	boolean isBusy();
	
	void destroy();
}
