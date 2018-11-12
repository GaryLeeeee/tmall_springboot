package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @Test
    public void get() throws Exception {
        Category category = categoryService.get(71);
        System.out.println(category.getName());
        Assert.assertEquals("男士西服2",category.getName());
        System.out.println("after");
    }

}