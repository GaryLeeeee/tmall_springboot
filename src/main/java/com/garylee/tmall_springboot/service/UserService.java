package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.User;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-29 11:52.
 */
public interface UserService {
    void add(User user);
    void delete(int id);
    void update(User user);
    User get(int id);
    List<User> list();
    boolean isExist(String name);
    User getUser(String name,String password);
}
