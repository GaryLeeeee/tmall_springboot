package com.garylee.tmall_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/index")
    public String index(){
        return "include/admin/adminNavigator";
    }
    @RequestMapping("admin_category_list")
    public String list(){
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_edit")
    public String edit(){
        return "admin/editCategory";
    }
    @RequestMapping("adminFooter")
    public String adminFooter(){
        return "include/admin/adminFooter";
    }
    @RequestMapping("adminHeader")
    public String adminHeader(){
        return "include/admin/adminHeader";
    }
    @RequestMapping("adminNavigator")
    public String adminNavigator(){
        return "include/admin/adminNavigator";
    }
    @RequestMapping("adminPage")
    public String adminPage(){
        return "include/admin/adminPage";
    }
}
