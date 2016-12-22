package de.himbiss.klim.servlets.beans;

import java.sql.Date;

/**
 * Created by Vincent on 17.12.2016.
 */
public class Comment {
    private final int id;
    private final int postId;
    private final Date creationTime;
    private final String content;

    public Comment(int id, int postId, Date creationTime, String content) {
        this.id = id;
        this.postId = postId;
        this.creationTime = creationTime;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getContent() {
        return content;
    }
}
