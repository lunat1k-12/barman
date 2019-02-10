package com.barman.barman.gpio.pump;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class PumpService implements IPumpService {

	private GpioPinDigitalOutput pumpOne;
	private GpioPinDigitalOutput pumpTwo;
	private GpioPinDigitalOutput pumpThree;
	private GpioController controller;

	public PumpService(GpioPinDigitalOutput pumpOne,
			           GpioPinDigitalOutput pumpTwo,
			           GpioPinDigitalOutput pumpThree){
		this.pumpOne = pumpOne;
		this.pumpTwo = pumpTwo;
		this.pumpThree = pumpThree;
		
	}

	public void setController(GpioController controller) {
		this.controller = controller;
	}

	@Override
	public GpioPinDigitalOutput getPumpOne() {
		return pumpOne;
	}

	@Override
	public GpioPinDigitalOutput getPumpTwo() {
		return pumpTwo;
	}

	@Override
	public GpioPinDigitalOutput getPumpThree() {
		return pumpThree;
	}

	@Override
	public boolean isBusy() {
        return pumpOne.isLow() || pumpTwo.isLow() || pumpThree.isLow();
	}

	@Override
	public void destroy() {
		controller.shutdown();
	}

}
