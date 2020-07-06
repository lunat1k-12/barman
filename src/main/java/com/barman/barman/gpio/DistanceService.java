package com.barman.barman.gpio;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class DistanceService implements IDistanceService
{
    private final GpioPinDigitalOutput triggerPin;
    
    private final GpioPinDigitalInput echoPin;

    private Long time;

    public DistanceService(GpioPinDigitalOutput triggerPin, GpioPinDigitalInput echo)
    {
        this.triggerPin = triggerPin;
        this.echoPin = echo;
    }

    @Override
    public GpioPinDigitalOutput getTriggerPin()
    {
        return triggerPin;
    }

    @Override
    public GpioPinDigitalInput getEchoPin()
    {
        return echoPin;
    }

    @Override
    public void setTime(Long time)
    {
        this.time = time;
    }

    @Override
    public Long getTime()
    {
        return time;
    }

}
