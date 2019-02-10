package com.barman.barman.gpio;

public class DistanceStateHolder implements IDistanceStateHolder
{

    private Long time;
    
    private volatile boolean close;

    @Override
    public Long getTime()
    {
        return time;
    }

    @Override
    public void setTime(Long time)
    {
        this.time = time;
    }

    @Override
    public boolean isClose()
    {
        return close;
    }

	@Override
	public void setClose(boolean close) {
		this.close = close;
	}

}
