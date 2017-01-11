package de.himbiss.klim.servlets.beans;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Vincent on 17.12.2016.
 */
public class Post {
    private int id;
    private User from;
    private int to;
    private Date date;
    private String content;
    private List<Photo> photos;

    public Post() {
    }

    public Post(int id, User creator, int to, Date date, String content, List<Photo> photos) {
        this.id = id;
        this.from = creator;
        this.date = date;
        this.to = to;
        this.content = content;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
