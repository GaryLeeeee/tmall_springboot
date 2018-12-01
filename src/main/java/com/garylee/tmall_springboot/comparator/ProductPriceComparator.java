package com.garylee.tmall_springboot.comparator;

import com.garylee.tmall_springboot.domain.Product;

import java.util.Comparator;

/**
 * Created by GaryLee on 2018-12-01 20:57.
 * 价格比较器
 * 价格低的放前面
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice()-p2.getPromotePrice());
    }
}
