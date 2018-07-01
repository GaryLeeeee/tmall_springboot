package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;
//    @RequestMapping("admin_property_add")
//    public String add(Property property){
//
//    }
//
//    public String delete(int id){
//
//    }
//
//    public String update(Property property){
//
//    }




}
