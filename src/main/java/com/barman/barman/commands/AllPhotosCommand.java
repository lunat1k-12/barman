package com.barman.barman.commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.dao.IPhotosDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.DbPhoto;

public class AllPhotosCommand extends AbstractCommandProcessor
{

    public static final String ALL_PHOTO = "все фото";

    @Autowired
    private IPhotosDao photosDao;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace)
    {

        if(ALL_PHOTO.equalsIgnoreCase(cspace.getRequestMessage()))
        {
            cspace.setResponseMessage("выгружаю все фото");
            Iterable<DbPhoto> dbPhotos = photosDao.findAll();

            List<InputStream> photos = new ArrayList<>();

            for(DbPhoto dbPhoto : dbPhotos)
            {
                photos.add(new ByteArrayInputStream(dbPhoto.getContent()));
            }
            cspace.setPhotos(photos);

            return cspace;
        }

        return processNextOrExit(cspace);
    }

}
