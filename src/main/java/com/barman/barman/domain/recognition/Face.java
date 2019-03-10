package com.barman.barman.domain.recognition;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Face {

    @JsonProperty("face_rectangle")
    private FaceRectangle faceRectangle;
    @JsonProperty("face_token")
    private String faceToken;

    public FaceRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public String getFaceToken() {
        return faceToken;
    }

    @Override
    public String toString() {
        return "Face{" +
                "faceRectangle=" + faceRectangle +
                ", faceToken='" + faceToken + '\'' +
                '}';
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }
}
