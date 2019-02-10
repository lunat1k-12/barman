package com.barman.barman.commands;

import com.barman.barman.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.camera.ICameraService;
import com.barman.barman.domain.CommandWorkspace;

public class DaysPhotosCommand extends AbstractCommandProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(DaysPhotosCommand.class);

	public static final String DAY_PHOTO = "фотографии за сутки";
	
	@Autowired
	private ICameraService cameraService;

	@Override
	public CommandWorkspace processCommand(CommandWorkspace cspace) {
		
		if(DAY_PHOTO.equalsIgnoreCase(cspace.getRequestMessage()))
		{
			if(Constants.N.equals(cspace.getUserPrivilege().getAllowAllPhoto())) {
				cspace.setRequestMessage(Constants.ACCESS_DENIED);
				return cspace;
			}

			LOG.info("Load day photos");
			cspace.setResponseMessage("фотогрфии за сутки");
			cspace.setPhotos(cameraService.getAllDayPhotos());
			return cspace;
		}

		return this.processNextOrExit(cspace);
	}

}
