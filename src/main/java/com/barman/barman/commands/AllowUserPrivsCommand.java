package com.barman.barman.commands;

import com.barman.barman.dao.IUserPrivilegesDao;
import com.barman.barman.domain.CommandWorkspace;
import com.barman.barman.domain.DbUserPrivilege;
import com.barman.barman.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.api.objects.Message;

public class AllowUserPrivsCommand extends AbstractCommandProcessor {

    public static final String ALLOW_COMMAND = "allow:";

    @Value("${admin.name}")
    private String adminName;

    @Autowired
    private IUserPrivilegesDao userPrivilegesDao;

    @Override
    public CommandWorkspace processCommand(CommandWorkspace cspace) {
        Message message = cspace.getMessage();

        if(cspace.getRequestMessage() != null && cspace.getRequestMessage().startsWith(ALLOW_COMMAND)) {

            cspace.setResponseMessage(adminName.equals(cspace.getUserPrivilege().getUserId()) ? grantPrivs(message.getText()) : Constants.ACCESS_DENIED);
            return cspace;
        }
        return processNextOrExit(cspace);
    }

    private String grantPrivs(String command) {
        String[] attributes = command.substring(6,command.length()).split(",");
        DbUserPrivilege userPrivs = userPrivilegesDao.findByUserId(attributes[0]);
        if(userPrivs == null) {
            return "user not found";
        }

        userPrivs.setAllowDrink(attributes[1]);
        userPrivs.setAllowPhoto(attributes[2]);
        userPrivs.setAllowAllPhoto(attributes[3]);
        userPrivilegesDao.save(userPrivs);

        return "Access granted";
    }
}
