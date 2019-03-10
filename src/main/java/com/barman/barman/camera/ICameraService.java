package com.barman.barman.camera;

import com.barman.barman.domain.ImageHolder;

import java.io.InputStream;
import java.util.List;

public interface ICameraService {

	InputStream takePicture();
	
	void deleteAllPhotos();
	
	List<InputStream> getAllDayPhotos();

	ImageHolder getImageData();
}
