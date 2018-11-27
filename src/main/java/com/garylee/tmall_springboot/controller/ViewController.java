package com.garylee.tmall_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;

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
    //后台跳转(即不需要uri)
    @RequestMapping("")
    public String admin(){
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
    //前台跳转
    @RequestMapping("index")
    public String index(HttpSession session, HttpServletRequest request){
        try {
            System.out.println(ResourceUtils.getFile("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "fore/home";
    }
}
