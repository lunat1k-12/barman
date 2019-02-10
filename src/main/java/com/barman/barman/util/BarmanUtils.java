package com.barman.barman.util;

import org.telegram.telegrambots.api.objects.Message;

public class BarmanUtils {

    public static String parseUserId(Message message) {
        return message.getFrom().getFirstName() + "/" + message.getFrom().getLastName();
    }
}
