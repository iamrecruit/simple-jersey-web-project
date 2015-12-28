package com.thoughtworks.learning.core;

import java.util.List;

public class Group {
    private String name;
    private int id;
    private List<User> members;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<User> getMembers() {
        return members;
    }
}
