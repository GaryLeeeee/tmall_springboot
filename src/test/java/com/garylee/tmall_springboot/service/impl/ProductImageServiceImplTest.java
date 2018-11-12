package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.service.ProductImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductImageServiceImplTest {
    @Autowired
    ProductImageService productImageService;
    @Test
    public void list() throws Exception {
//        System.out.println(productImageService.list(87));
    }

}