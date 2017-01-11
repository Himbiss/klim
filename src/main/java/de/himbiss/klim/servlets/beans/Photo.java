package de.himbiss.klim.servlets.beans;

import java.util.Date;

/**
 * Created by vincent on 29.12.16.
 */
public class Photo {
    private int id;
    private int userId;
    private String caption;
    private Date uploadTime;
    private String url;

    public Photo() {
    }

    public Photo(int id, int userId, String caption, Date uploadTime) {
        this.id = id;
        this.userId = userId;
        this.caption = caption;
        this.uploadTime = uploadTime;
        this.url = "/rest/download/img/" + id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
