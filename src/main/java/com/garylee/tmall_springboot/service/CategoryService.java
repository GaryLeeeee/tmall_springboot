package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    public List<Category> listAll();
    public int total();
    public void add(Category category);
    public void up(MultipartFile multipartFile,Category category);
    public void delete(int id);
    public Category get(int id);
    public void update(Category category);
}
