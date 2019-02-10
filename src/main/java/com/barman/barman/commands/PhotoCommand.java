package com.barman.barman.commands;

import java.io.InputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.camera.ICameraService;
import com.barman.barman.domain.CommandWorkspace;

public class PhotoCommand extends AbstractCommandProcessor
{

    private static final Logger LOG = LoggerFactory.getLogger(PhotoCommand.class);

    public static final String PHOTO = "фото";

    @Autowired
    private ICameraService cameraService;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace)
    {

        if(PHOTO.equalsIgnoreCase(cspace.getRequestMessage()))
        {
        	LOG.info("Take a picture");
        	cspace.setResponseMessage("фотографирую");
        	InputStream is = cameraService.takePicture();
        	cspace.setPhotos(Arrays.asList(is));
        	return cspace; 
        	
        }

        return this.processNextOrExit(cspace);
    }

}
