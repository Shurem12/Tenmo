package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    boolean create(String username, String password);

    boolean hasAccount(int userId);

    User findByUsername(String username);

    User findById(int userId);

    List<User> findAll();

//    int findIdByUsername(String username);

}
