package com.barman.barman.config;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import com.barman.barman.camera.CameraService;
import com.barman.barman.camera.ICameraService;
import com.barman.barman.commands.AllPhotosCommand;
import com.barman.barman.commands.CheckCupCommand;
import com.barman.barman.commands.DaysPhotosCommand;
import com.barman.barman.commands.DeletePhotoCommand;
import com.barman.barman.commands.ICommandProcessor;
import com.barman.barman.commands.NewDrinkCommand;
import com.barman.barman.commands.PhotoCommand;
import com.barman.barman.commands.PumpDrinkCommand;
import com.barman.barman.commands.message.IMessageProcessor;
import com.barman.barman.commands.message.MessageProcessor;
import com.barman.barman.gpio.DistanceService;
import com.barman.barman.gpio.DistanceStateHolder;
import com.barman.barman.gpio.EchoListener;
import com.barman.barman.gpio.IDistanceService;
import com.barman.barman.gpio.IDistanceStateHolder;
import com.barman.barman.gpio.pump.IPumpService;
import com.barman.barman.gpio.pump.PumpService;
import com.barman.barman.telegram.TelegramBot;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.impl.PinImpl;

@Configuration
public class BarmanConfig
{

    @Value("classpath:templates/drinks.json")
	private Resource confFile;

    @Bean
    public ICameraService getCameraService()
    {
    	return new CameraService();
    }

    @Bean
    public IMessageProcessor getMessageProcessor()
    {
    	return new MessageProcessor();
    }

    @Bean
    @Primary
    public ICommandProcessor getCommand()
    {
    	PhotoCommand command = new PhotoCommand();
    	command.setNext(getAllPhotosCommand())
    	       .setNext(getDaysPhotosCommand())
    	       .setNext(getDeletePhotoCommand())
    	       .setNext(getNewDrinkCommand())
    	       .setNext(getCheckCupCommand())
    	       .setNext(getPumpDrinkCommand());

    	return command;
    }
    
    @Bean
    public ICommandProcessor getDeletePhotoCommand()
    {
    	return new DeletePhotoCommand();
    }

    @Bean
    public ICommandProcessor getDaysPhotosCommand()
    {
    	return new DaysPhotosCommand();
    }

    @Bean
    public ICommandProcessor getCheckCupCommand()
    {
    	return new CheckCupCommand();
    }

    @Bean
    public ICommandProcessor getNewDrinkCommand()
    {
    	return new NewDrinkCommand();
    }

    @Bean
    public ICommandProcessor getAllPhotosCommand()
    {
    	return new AllPhotosCommand();
    }

    @Bean
    public ICommandProcessor getPumpDrinkCommand()
    {
    	return new PumpDrinkCommand();
    }

	@Bean
	public IPumpService getPumpService()
	{
        GpioController gpioController = GpioFactory.getInstance();
        GpioPinDigitalOutput pumpOne = gpioController.provisionDigitalOutputPin(createPin(5,"GPIO 5"),"PumpOne",
                PinState.HIGH);
        GpioPinDigitalOutput pumpTwo = gpioController.provisionDigitalOutputPin(createPin(4,"GPIO 4"),"PumpTwo",
                PinState.HIGH);
        GpioPinDigitalOutput pumpThree = gpioController.provisionDigitalOutputPin(createPin(1,"GPIO 1"),"PumpThree",
                PinState.HIGH);

        pumpOne.setShutdownOptions(true,PinState.HIGH);
        pumpTwo.setShutdownOptions(true,PinState.HIGH);
        pumpThree.setShutdownOptions(true,PinState.HIGH);

        PumpService pumpService = new PumpService(pumpOne,pumpTwo,pumpThree);
        pumpService.setController(gpioController);

        return pumpService;
	}

    @Bean
    public IDistanceService getDistanceService()
    {
        GpioController gpioController = GpioFactory.getInstance();
        GpioPinDigitalOutput triger = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03,"triger",
                PinState.HIGH);
        
        GpioPinDigitalInput echo = gpioController.provisionDigitalInputPin(createInputPin(2, "GPIO 2"),PinPullResistance.PULL_DOWN);
        echo.setShutdownOptions(true);
        echo.addListener(getEchoListener());

        return new DistanceService(triger,echo);
    }
    
    @Bean
    public GpioPinListenerDigital getEchoListener()
    {
        return new EchoListener();
    }

    @Bean
    public IDistanceStateHolder getDistanceStateHolder()
    {
        return new DistanceStateHolder();
    }

	private Pin createPin(int pos, String name)
    {
        return new PinImpl(RaspiGpioProvider.NAME,pos,name,EnumSet.of(PinMode.DIGITAL_OUTPUT),PinPullResistance.all());
    }

	private Pin createInputPin(int pos, String name)
	{
	    return new PinImpl(RaspiGpioProvider.NAME,pos,name,EnumSet.of(PinMode.DIGITAL_INPUT),PinPullResistance.all());
	}

    @Bean
    public TelegramBot getBot()
    {
        return new TelegramBot();
    }
}
