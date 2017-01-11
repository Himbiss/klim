package de.himbiss.klim;

/**
 * Created by vincent on 25.12.16.
 */
public class FollowRequest {
    private String userId;
    private String followerId;

    public FollowRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
