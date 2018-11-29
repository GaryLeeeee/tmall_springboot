package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Test
    public void isExist() throws Exception {
        System.out.println(userService.isExist("测试用户1"));
        System.out.println(userService.isExist("测试用户8"));
    }
    @Test
    public void getUser() throws Exception {
        User user = userService.getUser("测试用户1","password1");
        System.out.println(null!=user);
        User user2 = userService.getUser("1234","123");
        System.out.println(null!=user2);
    }

}