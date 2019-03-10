package com.barman.barman.domain.recognition;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecognitionResponse {

    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("time_used")
    private Long timeUsed;

    @JsonProperty("faces")
    private List<Face> faces;

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Long timeUsed) {
        this.timeUsed = timeUsed;
    }

    @Override
    public String toString() {
        return "RecognitionResponse{" +
                "imageId='" + imageId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", timeUsed=" + timeUsed +
                ", faces=" + faces +
                '}';
    }
}
