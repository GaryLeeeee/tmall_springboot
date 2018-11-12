package com.garylee.tmall_springboot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class PropertyControllerTest {
    @Autowired
    PropertyController propertyController;
    @Test
    public void getAll() throws Exception {
        System.out.println(propertyController.getAll(82,0,6));
    }

}