package com.barman.barman.gpio;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class DistanceService implements IDistanceService
{
    private GpioPinDigitalOutput trigerPin;
    
    private GpioPinDigitalInput echoPin;

    private Long time;

    public DistanceService(GpioPinDigitalOutput triger, GpioPinDigitalInput echo)
    {
        this.trigerPin = triger;
        this.echoPin = echo;
    }

    @Override
    public GpioPinDigitalOutput getTrigerPin()
    {
        return trigerPin;
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
