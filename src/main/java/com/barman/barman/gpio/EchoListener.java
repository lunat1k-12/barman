package com.barman.barman.gpio;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class EchoListener implements GpioPinListenerDigital
{

    private static final Logger LOG = LoggerFactory.getLogger(EchoListener.class);

    @Autowired
    private IDistanceStateHolder state;

    @Value("${distance.average}")
    private long average;

    @Value("${distance.max}")
    private long distanceMax;
    
    private List<Long> values = new ArrayList<>();

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
    {
        if(0 == event.getState().getValue())
        {
        	values.add(System.nanoTime() - state.getTime());
        	if(average == values.size())
        	{
        		boolean close = this.isClose();
        		
        		if(close != state.isClose())
                {
                	String msg = close ? "CUP" : "NO CUP";
                	LOG.info(msg);
                }

                state.setClose(close);
        	}
        }
    }
    
    private boolean isClose()
    {
    	long averageDistance = values.stream().mapToLong(l -> l).sum() / average;
    	values.clear();

        LOG.info("Distance - " + averageDistance);
    	return averageDistance < distanceMax;
    }

}
