package com.leif2k.messenger;

public class User {

    private String id;
    private String name;
    private String lastName;
    private int age;
    private boolean online;


    public User() {
    }

    public User(String id, String name, String lastName, int age, Boolean online) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.online = online;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Boolean getOnline() {
        return online;
    }

}
