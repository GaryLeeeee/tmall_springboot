package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.CategoryMapper;
import com.garylee.tmall_springboot.dao.ProductMapper;
import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.ProductExample;
import com.garylee.tmall_springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-13 21:55.
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");

        List<Product> products = productMapper.selectByExample(productExample);
        setCategory(products);
        return products;
    }

    public void setCategory(Product product){
        Category category = categoryMapper.selectByPrimaryKey(product.getCid());
        product.setCategory(category);
    }
    public void setCategory(List<Product> products){
        for(Product product:products)
            setCategory(product);
    }
}
