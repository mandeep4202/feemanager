/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Mandeep Singh
 */
public class User {

    private int userId;
    private String userName;
    private String password;
    private Date lastLoginDate;
    private Time lastLoginTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Time getLastLoginTIme() {
        return lastLoginTime;
    }

    public void setLastLoginTIme(Time lastLoginTIme) {
        this.lastLoginTime = lastLoginTIme;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + '}';
    }

}
