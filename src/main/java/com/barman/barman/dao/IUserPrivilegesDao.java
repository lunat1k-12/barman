package com.barman.barman.dao;

import com.barman.barman.domain.DbUserPrivilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserPrivilegesDao extends CrudRepository<DbUserPrivilege, String> {

    DbUserPrivilege findByUserId(String userId);

    @Query(value = "SELECT * FROM USER_PRIVILEGES WHERE ALLOW_DRINK = 'N' OR ALLOW_PHOTO = 'N' OR ALLOW_ALL_PHOTO = 'N'", nativeQuery = true)
    List<DbUserPrivilege> loadNotGranted();
}
