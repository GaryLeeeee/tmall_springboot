package com.garylee.tmall_springboot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class PropertyValueControllerTest {
    @Autowired
    PropertyValueController propertyValueController;
    @Test
    public void getAll() throws Exception {
        System.out.println(propertyValueController.getAll(204));
    }

}