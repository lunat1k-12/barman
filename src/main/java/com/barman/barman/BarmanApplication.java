package com.barman.barman;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.barman.barman.gpio.pump.IPumpService;
import com.barman.barman.telegram.TelegramBot;

// to run use sudo java -jar -Dpi4j.linking=dynamic target/barman.jar
@SpringBootApplication
@EnableScheduling
public class BarmanApplication implements CommandLineRunner
{

	public static void main(String[] args) {
		SpringApplication.run(BarmanApplication.class, args);
	}

    {
        ApiContextInitializer.init();
    }

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private IPumpService pumpService;

    @Override
    public void run(String... arg0) throws Exception
    {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    @PreDestroy
    public void destroy()
    {
    	pumpService.destroy();
    }
}
