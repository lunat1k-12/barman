package com.barman.barman.domain.recognition;

import java.util.Objects;

public class FaceRectangle {

    private Integer width;
    private Integer top;
    private Integer left;
    private Integer height;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getTop() {
        return top;
    }

    @Override
    public String toString() {
        return "FaceRectangle{" +
                "width=" + width +
                ", top=" + top +
                ", left=" + left +
                ", height=" + height +
                '}';
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaceRectangle that = (FaceRectangle) o;
        return Objects.equals(width, that.width) &&
                Objects.equals(top, that.top) &&
                Objects.equals(left, that.left) &&
                Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {

        return Objects.hash(width, top, left, height);
    }
}
