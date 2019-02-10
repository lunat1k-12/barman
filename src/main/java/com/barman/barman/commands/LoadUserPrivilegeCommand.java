package com.barman.barman.commands;

import com.barman.barman.dao.IUserPrivilegesDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.DbUserPrivilege;
import com.barman.barman.util.BarmanUtils;
import com.barman.barman.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class LoadUserPrivilegeCommand extends AbstractCommandProcessor {

    @Value("${admin.name}")
    private String adminName;

    @Autowired
    private IUserPrivilegesDao userPrivilegesDao;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace) {

        String userId = BarmanUtils.parseUserId(cspace.getMessage());
        cspace.setUserPrivilege(adminName.equals(userId) ? this.getAdminMock() : this.getOrCreatePrivilege(userId));
        return processNextOrExit(cspace);
    }

    private DbUserPrivilege getAdminMock() {
        DbUserPrivilege admin = new DbUserPrivilege();
        admin.setUserId(adminName);
        admin.setAllowAllPhoto(Constants.Y);
        admin.setAllowDrink(Constants.Y);
        admin.setAllowPhoto(Constants.Y);
        return admin;
    }

    private DbUserPrivilege getOrCreatePrivilege(String userId) {
        DbUserPrivilege user = userPrivilegesDao.findByUserId(userId);
        return user != null ? user : userPrivilegesDao.save(new DbUserPrivilege(userId));
    }
}
