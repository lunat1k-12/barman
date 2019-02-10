package com.barman.barman.camera;

import java.io.InputStream;
import java.util.List;

public interface ICameraService {

	InputStream takePicture();
	
	void deleteAllPhotos();
	
	List<InputStream> getAllDayPhotos();
}
