package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.CategoryMapper;
import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Category> list(Page page){
        return categoryMapper.list(page);
    }

    @Override
    public List<Category> listAll() {
        return categoryMapper.listAll();
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }
}
