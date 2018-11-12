package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by GaryLee on 2018-07-13 21:48.
 */
@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @RequestMapping("admin_product_list")
    public String index(){
        return "admin/listProduct";
    }
    @RequestMapping("admin_product_add")
    public String add(Product product){
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }
    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }
    @RequestMapping("admin_product_edit")
    public String edit(){
        return "admin/editProduct";
    }
    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }
    @RequestMapping("listProduct")
    @ResponseBody
    public PageInfo getAll(@RequestParam("cid") int cid, @RequestParam(value = "start",defaultValue = "0")int start, @RequestParam(value = "size",defaultValue = "5")int size){
        PageHelper.startPage(start,size);
        PageInfo<Product> page = new PageInfo<>(productService.list(cid));
        return page;
    }
    @RequestMapping("getProduct")
    @ResponseBody
    public Product get(@RequestParam("id") int id){
        return productService.get(id);
    }
}
