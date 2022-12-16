package com.example.foodchoice.HelperClasses;

public class UserClass {
    String user_id, user_fullName, user_UserName, user_UseEmail, user_UserPhone, user_UserPassword;

    ////empty constructor///
    public UserClass() {
    }
    ///without user_id/////
    public UserClass(String user_fullName, String user_UserName, String user_UseEmail, String user_UserPhone, String user_UserPassword) {
        this.user_fullName = user_fullName;
        this.user_UserName = user_UserName;
        this.user_UseEmail = user_UseEmail;
        this.user_UserPhone = user_UserPhone;
        this.user_UserPassword = user_UserPassword;
    }

    ////with user_id///
    public UserClass(String user_id, String user_fullName, String user_UserName, String user_UseEmail, String user_UserPhone, String user_UserPassword) {
        this.user_id = user_id;
        this.user_fullName = user_fullName;
        this.user_UserName = user_UserName;
        this.user_UseEmail = user_UseEmail;
        this.user_UserPhone = user_UserPhone;
        this.user_UserPassword = user_UserPassword;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullName() {
        return user_fullName;
    }

    public void setUser_fullName(String user_fullName) {
        this.user_fullName = user_fullName;
    }

    public String getUser_UserName() {
        return user_UserName;
    }

    public void setUser_UserName(String user_UserName) {
        this.user_UserName = user_UserName;
    }

    public String getUser_UseEmail() {
        return user_UseEmail;
    }

    public void setUser_UseEmail(String user_UseEmail) {
        this.user_UseEmail = user_UseEmail;
    }

    public String getUser_UserPhone() {
        return user_UserPhone;
    }

    public void setUser_UserPhone(String user_UserPhone) {
        this.user_UserPhone = user_UserPhone;
    }

    public String getUser_UserPassword() {
        return user_UserPassword;
    }

    public void setUser_UserPassword(String user_UserPassword) {
        this.user_UserPassword = user_UserPassword;
    }
}
