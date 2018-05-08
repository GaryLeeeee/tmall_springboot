package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.util.Page;

import java.util.List;

public interface CategoryService {
    public List<Category> list(Page page);
    public List<Category> listAll();
    public int total();
    public Category add();
}
