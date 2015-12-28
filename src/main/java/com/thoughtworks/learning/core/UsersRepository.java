package com.thoughtworks.learning.core;

import java.util.List;
import java.util.Map;

public interface UsersRepository {
    List<User> findUsers();

    User newUser(String name);

    void createUser(Map newInstance);

    User getUserById(int userId);
}
