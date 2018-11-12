package com.garylee.tmall_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
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
    //首页跳转(即不需要uri)
    @RequestMapping("")
    public String index(){
        return "admin/listCategory";
    }
    @RequestMapping("404")
    public String error(){
        return "index";
    }
    @RequestMapping("vue")
    public String vue(){
        return "vue";
    }
    @RequestMapping("vueCategory")
    public String v(){
        return "admin/vueCategory";
    }
}
