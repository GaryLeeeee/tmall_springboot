package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by GaryLee on 2018-11-13 09:00.
 */
@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @RequestMapping("forehome")
    @ResponseBody
    public List<Category> forehome(){
        List<Category> categories = categoryService.listAll();
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }
}
