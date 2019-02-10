package com.barman.barman.gpio;

public interface IDistanceStateHolder
{

    Long getTime();
    
    void setTime(Long Time);
    
    boolean isClose();

    void setClose(boolean close);
}
