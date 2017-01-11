package de.himbiss.klim;

/**
 * Created by vincent on 25.12.16.
 */
public class PostRequest {
    private int userId;
    private int profileId;
    private String content;

    public PostRequest() {
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
