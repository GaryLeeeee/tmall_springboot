package com.garylee.tmall_springboot.comparator;

import com.garylee.tmall_springboot.domain.Product;

import java.util.Comparator;

/**
 * Created by GaryLee on 2018-12-01 20:57.
 * 销量比较器
 * 把销量高的放前面
 */
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }
}
