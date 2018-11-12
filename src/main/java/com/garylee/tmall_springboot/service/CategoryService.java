package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> listAll();
    int total();
    void add(Category category);
    //图片存储
    void up(MultipartFile multipartFile,Category category);
    void delete(int id);
    Category get(int id);
    void update(Category category);
}
