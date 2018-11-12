package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-16 13:47.
 */
public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";
    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);
    List<ProductImage> list(int pid,String type);
    List<ProductImage> list(int pid);
    //图片存储
    void up(MultipartFile multipartFile,ProductImage productImage);
}
