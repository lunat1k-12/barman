package com.barman.barman.domain;

import java.awt.image.BufferedImage;

public class ImageHolder {

    private byte[] imageBytes;

    private BufferedImage image;

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
