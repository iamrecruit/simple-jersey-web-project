package com.thoughtworks.learning.core;

import java.util.List;
import java.util.Map;

public interface GroupsRepository {

    List<Group> findGroups();

    void createGroup(Map newInstance);

    Group getGroupById(int id);
    
}
