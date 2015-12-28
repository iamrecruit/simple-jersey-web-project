package com.thoughtworks.learning.core;

import java.util.List;

public class User {
    private String name;
    private int id;
    private List<Contact> contacts;
    private List<Group> groups;

    public User(Integer id, String name, List<Contact> contacts, List<Group> groups) {
        this.name = name;
        this.id = id;
        this.contacts = contacts;
        this.groups = groups;
    }

    public User(String name) {

        this.name = name;
    }
    
    public User(){}

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Contact> getContacts(){
        return contacts;
        
    };

    public List<Group> getGroups() {
        return groups;
    }
}
