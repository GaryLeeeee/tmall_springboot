package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.CategoryMapper;
import com.garylee.tmall_springboot.dao.ProductMapper;
import com.garylee.tmall_springboot.dao.ReviewMapper;
import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.ProductExample;
import com.garylee.tmall_springboot.domain.ProductImage;
import com.garylee.tmall_springboot.service.OrderItemService;
import com.garylee.tmall_springboot.service.ProductImageService;
import com.garylee.tmall_springboot.service.ProductService;
import com.garylee.tmall_springboot.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaryLee on 2018-07-13 21:55.
 */
// TODO: 2018/11/13 0013 setFirstProductImage
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
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
        setFirstProductImage(product);
        setCategory(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");

        List<Product> products = productMapper.selectByExample(productExample);
        setFirstProductImage(products);
        setCategory(products);
        return products;
    }

    @Override
    public void fill(List<Category> categories) {
        for(Category category:categories)
            fill(category);
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for(Category category:categories){
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i=0;i<products.size();i+=productNumberEachRow){
                int size = i + productNumberEachRow;
                size = size>products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i,size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        //根据订单项求出销量
        int saleCount = orderItemService.getSaleCount(product.getId());
        product.setSaleCount(saleCount);
        //根据评论条数求出评论数
        int reviewCount = reviewService.getCount(product.getId());
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for(Product product:products)
            setSaleAndReviewNumber(product);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        //%表示任意个字符
        example.createCriteria().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        //设置第一张图
        setFirstProductImage(products);
        //设置销量和评价
        setSaleAndReviewNumber(products);
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

    public void setFirstProductImage(Product product) {
        //只查询single类(不加ProductImageService.type_single则查询全部,包含detail)
        List<ProductImage> productImages = productImageService.list(product.getId(), ProductImageService.type_single);
        if(!productImages.isEmpty()){
            ProductImage productImage = productImages.get(0);
            product.setFirstProductImage(productImage);
        }
    }
    public void setFirstProductImage(List<Product> products) {
        for(Product product:products)
            setFirstProductImage(product);
    }
}
