package com.barman.barman.gpio;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public interface IDistanceService
{

    void setTime(Long time);

    Long getTime();

    GpioPinDigitalOutput getTrigerPin();
    
    GpioPinDigitalInput getEchoPin();
}
