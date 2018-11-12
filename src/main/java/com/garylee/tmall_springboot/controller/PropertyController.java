package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.service.PropertyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;
    @RequestMapping("admin_property_add")
    public String add(Property property){
        System.out.println(property.getId());
        System.out.println(property.getCid());
        System.out.println(property.getName());
        propertyService.add(property);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }
    @RequestMapping("admin_property_delete")
    public String delete(int id){
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }
    @RequestMapping("admin_property_update")
    public String update(Property property){
        propertyService.update(property);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }
    @RequestMapping("admin_property_edit")
    public String edit(){
        return "admin/editProperty";
    }
    @RequestMapping("listProperty")
    @ResponseBody
    public PageInfo getAll(@RequestParam("cid") int cid, @RequestParam(value = "start",defaultValue = "0")int start,@RequestParam(value = "size",defaultValue = "5")int size){
        PageHelper.startPage(start,size);
        PageInfo<Property> page = new PageInfo<>(propertyService.list(cid));
        return page;
    }
//    跳转到分类界面
    @RequestMapping("admin_property_list")
    public String list(){
        return "admin/listProperty";
    }
    @RequestMapping("getProperty")
    @ResponseBody
    public Property get(@RequestParam("id") int id){
        return propertyService.get(id);
    }
}
