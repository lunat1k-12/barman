package com.barman.barman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.barman.barman.domain.DbPhoto;

public interface IPhotosDao extends CrudRepository<DbPhoto, Long>{

	@Query(value = "SELECT * FROM PHOTOS WHERE DATE > (SYSDATE - 1.0/24)", nativeQuery = true)
	List<DbPhoto> getDayPhotos();

}
