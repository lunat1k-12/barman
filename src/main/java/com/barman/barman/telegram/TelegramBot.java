package com.barman.barman.telegram;

import com.barman.barman.commands.message.IMessageProcessor;
import com.barman.barman.domain.CommandWorkspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.List;

import static com.barman.barman.util.Constants.ADMIN_CHAT;

public class TelegramBot extends TelegramLongPollingBot
{

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    public static final String BOT_USERNAME = "AkvelonBarman_bot";

    public static final String BOT_TOKEN = "454786146:AAF0jKAwZt6Q_45wb4qE1DiweL5Y5qOTqfQ";

    @Autowired
    private IMessageProcessor messageProcessor;

    @Override
    public String getBotUsername()
    {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if(update.hasMessage())
        {
            Message message = update.getMessage();

            if(message.hasText())
            {
                LOG.info("Recieved message from - {}",message.getFrom().getFirstName());
                processRequest(message);
            }
        }

    }

    public void sendAdminMessage(String message) throws TelegramApiException {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(Long.parseLong(ADMIN_CHAT));
        sendMessageRequest.setText(message);

        sendApiMethod(sendMessageRequest);
    }

    private void processRequest(Message message)
    {
        try
        {
            CommandWorkspace result = messageProcessor.processMessage(message);

            SendMessage sendMessageRequest = new SendMessage();
            sendMessageRequest.setChatId(message.getChatId().toString());
            sendMessageRequest.setText(result.getResponseMessage());

            sendApiMethod(sendMessageRequest);

            if(result.getPhotos() != null && result.getPhotos().size() > 0)
            {
                sendPhotos(message.getChatId().toString(),result.getPhotos());
            }

        }
        catch(TelegramApiException e)
        {
            LOG.error("TelegramApiException: {}",e);
        }
    }

    private void sendPhotos(String chatId, List<InputStream> photos) throws TelegramApiException
    {
        for(InputStream photo : photos)
        {
            SendPhoto pic = new SendPhoto();
            pic.setChatId(chatId);
            pic.setNewPhoto("pic",photo);

            sendPhoto(pic);
        }
    }

    @Override
    public String getBotToken()
    {
        return BOT_TOKEN;
    }
}
