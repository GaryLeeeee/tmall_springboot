package com.garylee.tmall_springboot.comparator;

import com.garylee.tmall_springboot.domain.Product;

import java.util.Comparator;

/**
 * Created by GaryLee on 2018-12-01 20:56.
 * 人气比较器
 * 把评价量高的放前面
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()-p1.getReviewCount();
    }
}
