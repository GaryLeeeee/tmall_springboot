package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.service.impl.CategoryServiceImpl;
import com.garylee.tmall_springboot.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequestMapping("listCategory")
    @ResponseBody
    /**
     * @start 当前页数
     * @size 每页个数
     */
    public PageInfo getAll(@RequestParam(value = "start",defaultValue = "0")int start,@RequestParam(value = "size",defaultValue = "5")int size){
        PageHelper.startPage(start,size);
        PageInfo<Category> page = new PageInfo<>(categoryService.listAll());
        return page;
    }
}
