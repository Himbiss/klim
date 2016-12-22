package de.himbiss.klim.servlets.beans;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Vincent on 17.12.2016.
 */
public class Post {
    private int id;
    private int from;
    private int to;
    private Date date;
    private String content;

    public Post() {
    }

    public Post(int id, int creator, int to, Date date, String content) {
        this.id = id;
        this.from = creator;
        this.date = date;
        this.to = to;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
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
}
