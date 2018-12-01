package com.garylee.tmall_springboot.comparator;

import com.garylee.tmall_springboot.domain.Product;

import java.util.Comparator;

/**
 * Created by GaryLee on 2018-12-01 20:56.
 * 新品比较器
 * 按照创建日期排序
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
