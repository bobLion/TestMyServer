package com.newproject.bob.testmyserver;

import java.io.Serializable;

public class GirlEntity implements Serializable {
    private int id;
    private String userName;
    private String password;
    private String age;

    public GirlEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}