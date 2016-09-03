package com.example.asus.news.entiy;

/**
 *
 * 文章中的图片
 * Created by JiangAo on 2016/9/1.
 */
public class Picture {
    String file;
    String fileHD;
    String description;
    int width;
    int height;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileHD() {
        return fileHD;
    }

    public void setFileHD(String fileHD) {
        this.fileHD = fileHD;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
