package com.garylee.tmall_springboot.comparator;

import com.garylee.tmall_springboot.domain.Product;

import java.util.Comparator;

/**
 * Created by GaryLee on 2018-12-01 20:56.
 * 综合比较器
 * 分类页的综合排序(销量*评价)
 */
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
    }
}
