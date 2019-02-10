package com.barman.barman.domain;

import java.io.InputStream;
import java.util.List;

import org.telegram.telegrambots.api.objects.Message;

public class CommandWorkspace {

	private String requestMessage;
	
	private String responseMessage;
	
	private List<InputStream> photos;

	private Message message;
	
	private boolean cupClose;

	public boolean isCupClose()
    {
        return cupClose;
    }

    public void setCupClose(boolean cupClose)
    {
        this.cupClose = cupClose;
    }

    public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<InputStream> getPhotos() {
		return photos;
	}

	public void setPhotos(List<InputStream> photos) {
		this.photos = photos;
	}
}
