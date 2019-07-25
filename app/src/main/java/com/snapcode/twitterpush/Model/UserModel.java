package com.snapcode.twitterpush.Model;

public class UserModel {

    private String userName;
    private String userId;
    private String userEmail;
    private String userPhoto;
    private String pushId;
    private String userToken;

    public UserModel() {
    }

    public UserModel(String userName, String userId, String userEmail, String userPhoto, String pushId, String userToken) {
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
        this.pushId = pushId;
        this.userToken = userToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
