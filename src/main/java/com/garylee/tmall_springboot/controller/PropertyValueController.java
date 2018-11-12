package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.dao.ProductMapper;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.domain.PropertyValue;
import com.garylee.tmall_springboot.service.ProductService;
import com.garylee.tmall_springboot.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-27 23:34.
 */
@Controller
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;
    @RequestMapping("admin_propertyValue_edit")
    public String edit(int pid){
        Product product = productService.get(pid);
        propertyValueService.init(product);

        return "admin/editPropertyValue";
    }
    //跳转页面(同时对product初始化if必要，跳转后通过ajax获取product对应的property(在后者实体内)和propertyValue)
    @RequestMapping("listPropertyValue")
    @ResponseBody
    public List<PropertyValue> getAll(int pid){
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        return propertyValues;
    }
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue){
        propertyValueService.update(propertyValue);
        return "success";
    }
}
