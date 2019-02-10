package com.barman.barman.camera;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.barman.barman.dao.IPhotosDao;
import com.barman.barman.domain.DbPhoto;
import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

public class CameraService implements ICameraService {

	private static final Logger LOG = LoggerFactory.getLogger(CameraService.class);
	
	@Autowired
    private IPhotosDao photosDao;

	@Override
	public InputStream takePicture() {
		InputStream result = null;
		try
        {
            RPiCamera camera = getCamera();

            BufferedImage img = camera.takeBufferedStill();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img,"jpg",os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            DbPhoto photo = saveImage(is);
            result = new ByteArrayInputStream(photo.getContent());

        }
        catch(Exception ex)
        {
            LOG.error("Photo Failed {}",ex);
        }

		return result;
	}

	@Override
	public void deleteAllPhotos() 
	{
		photosDao.deleteAllPhotos();
	}

	@Override
	public List<InputStream> getAllDayPhotos() 
	{
		List<DbPhoto> dayPhotos = photosDao.getDayPhotos();
		
		return dayPhotos.parallelStream().map(dbPhoto -> new ByteArrayInputStream(dbPhoto.getContent()))
				                         .collect(Collectors.toList());
	}

	private RPiCamera getCamera() throws FailedToRunRaspistillException
    {

        RPiCamera piCamera = new RPiCamera();
        piCamera.setWidth(976);
        piCamera.setHeight(1296);
        piCamera.setBrightness(60);
        piCamera.setExposure(Exposure.AUTO);
        piCamera.setAddRawBayer(true);
        piCamera.setTimeout(2);

        return piCamera;
    }

    private DbPhoto saveImage(InputStream pic) throws IOException
    {
        DbPhoto photo = new DbPhoto();
        photo.setDate(new Date());
        photo.setContent(IOUtils.toByteArray(pic));

        return photosDao.save(photo);
    }
}
