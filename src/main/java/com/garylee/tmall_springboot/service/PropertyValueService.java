package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.PropertyValue;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-27 20:03.
 */
public interface PropertyValueService {
    void init(Product product);
    void update(PropertyValue propertyValue);

    PropertyValue get(int pid,int ptid);
    List<PropertyValue> list(int pid);
}
