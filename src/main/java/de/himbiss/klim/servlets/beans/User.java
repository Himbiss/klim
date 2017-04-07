package de.himbiss.klim.servlets.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * Created by Vincent on 17.12.2016.
 */
public class User {
    private int id;
    private String userName;
    private List<String> description;
    private String avatarImg;
    private String backgroundImg;
    private Date creationTime;
    private String email;
    private String role;
    private String passwordHash;

    public User() {
    }

    public User(int id, String userName, List<String> description, String avatarImg, String backgroundImg, Date creationTime, String email, String role, String passwordHash) {
        this.id = id;
        this.userName = userName;
        this.description = description;
        this.avatarImg = avatarImg;
        this.backgroundImg = backgroundImg;
        this.creationTime = creationTime;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getDescription() {
        return description;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
