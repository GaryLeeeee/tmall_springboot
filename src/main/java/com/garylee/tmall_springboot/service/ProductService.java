package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List<Product> list(int cid);

    void fill(List<Category> categories);
    void fill(Category category);
    void fillByRow(List<Category> categories);
    //设置销量和评价数量
    void setSaleAndReviewNumber(Product product);
    void setSaleAndReviewNumber(List<Product> products);

    List<Product> search(String keyword);
}
