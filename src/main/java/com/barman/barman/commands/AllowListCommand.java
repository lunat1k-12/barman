package com.barman.barman.commands;

import com.barman.barman.dao.IUserPrivilegesDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.DbUserPrivilege;
import com.barman.barman.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class AllowListCommand extends AbstractCommandProcessor {

    public static final String ALLOW_LIST_COMMAND = "allow list";

    @Value("${admin.name}")
    private String adminName;

    @Autowired
    private IUserPrivilegesDao userPrivilegesDao;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace) {

        if(cspace.getRequestMessage() != null && cspace.getRequestMessage().trim().equals(ALLOW_LIST_COMMAND)) {

            cspace.setResponseMessage(adminName.equals(cspace.getUserPrivilege().getUserId()) ? this.getAllowList() : Constants.ACCESS_DENIED);
            return cspace;
        }
        return processNextOrExit(cspace);
    }

    private String getAllowList() {
        List<DbUserPrivilege> users = userPrivilegesDao.loadNotGranted();

        return users.stream().map(u -> u.getUserId() + ": " + u.getAllowDrink() + u.getAllowPhoto() + u.getAllowAllPhoto())
                .reduce((left, right) -> left + "\n" + right + "\n").orElse("empty");
    }
}
