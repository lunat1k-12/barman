package com.barman.barman.schedule.service;

import com.barman.barman.camera.ICameraService;
import com.barman.barman.domain.ImageHolder;
import com.barman.barman.domain.recognition.Face;
import com.barman.barman.domain.recognition.FaceRectangle;
import com.barman.barman.domain.recognition.RecognitionResponse;
import com.barman.barman.telegram.TelegramBot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.barman.barman.util.Constants.ADMIN_CHAT;

@Service
public class RecognitionService implements IRecognitionService {

    private static final Logger LOG = LoggerFactory.getLogger(RecognitionService.class);

    @Autowired
    private TelegramBot bot;
    @Autowired
    private ICameraService cameraService;
    @Autowired
    private IHttpService httpService;

    @Override
    public void checkPerson() {

        String response = null;
        try {
            ImageHolder image = cameraService.getImageData();
            response = httpService.scanImage(image.getImageBytes());
            LOG.info(response);
            ObjectMapper mapper = new ObjectMapper();
            RecognitionResponse res = mapper.readValue(response,RecognitionResponse.class);

            if(!CollectionUtils.isEmpty(res.getFaces())) {
                bot.sendAdminMessage("Enemy spotted");
                processFaces(res.getFaces(), image.getImage());
            }
        } catch(Exception ex) {
            LOG.error("response: \n" + response);
        }
    }

    private void processFaces(List<Face> faces, BufferedImage image) throws IOException, TelegramApiException {
        for(Face face : faces) {
            FaceRectangle rec = face.getFaceRectangle();
            BufferedImage faceImg = image.getSubimage(rec.getLeft(),rec.getTop(),rec.getWidth(),rec.getHeight());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(faceImg,"jpeg",os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            SendPhoto pic = new SendPhoto();
            pic.setChatId(Long.parseLong(ADMIN_CHAT));
            pic.setNewPhoto("pic",is);

            bot.sendPhoto(pic);
        }
    }
}
