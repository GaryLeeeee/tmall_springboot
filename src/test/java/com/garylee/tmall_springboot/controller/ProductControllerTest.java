package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTest {
    @Autowired
    ProductController productController;
    @Test
    public void getAll() throws Exception {
        System.out.println(productController.getAll(82,0,6));
    }
    @Test
    public void get(){
        Product product = productController.get(90);
        Category category = product.getCategory();
        System.out.println(category.getName());
    }

}