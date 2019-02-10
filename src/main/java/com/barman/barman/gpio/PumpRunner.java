package com.barman.barman.gpio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class PumpRunner extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(PumpRunner.class);

	private GpioPinDigitalOutput pump;
	private long time;
	
	public PumpRunner(GpioPinDigitalOutput pump, long time)
	{
		this.pump = pump;
		this.time = time;
	}

	@Override
	public void run() {
		
		if(time <= 0)
		{
			return;
		}

        LOG.info("Run pump for:{}",time);
		try {
            pump.low();
            Thread.sleep(time);
			pump.high();
		} catch (InterruptedException e) {
            LOG.error("Run pump Error",e);
		}
	}

	
}
