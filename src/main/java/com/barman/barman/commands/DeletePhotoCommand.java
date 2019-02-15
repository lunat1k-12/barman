package com.barman.barman.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.camera.ICameraService;
import com.barman.barman.domain.CommandWorkspace;

public class DeletePhotoCommand extends AbstractCommandProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(DeletePhotoCommand.class);

	public static final String DELETE_PHOTO = "удалить фотографии";

	@Autowired
    private ICameraService cameraService;

	@Override
	public CommandWorkspace processCommand(CommandWorkspace cspace) {
		
		if(DELETE_PHOTO.equalsIgnoreCase(cspace.getRequestMessage()))
		{
			LOG.info("Delete photos");
			cspace.setResponseMessage("фотогрфии удалены");
			cameraService.deleteAllPhotos();
			return cspace;
		}

		return this.processNextOrExit(cspace);
	}

}
