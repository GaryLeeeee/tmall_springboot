package com.garylee.tmall_springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by GaryLee on 2018-11-29 00:40.
 * 前台页面跳转
 */
@Controller
public class ForeViewController {
    @GetMapping(value="/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping(value="/home")
    public String home(){
        return "fore/home";
    }
    @GetMapping(value="/register")
    public String register(){
        return "fore/register";
    }
    @GetMapping(value="/alipay")
    public String alipay(){
        return "fore/alipay";
    }
    @GetMapping(value="/bought")
    public String bought(){
        return "fore/bought";
    }
    @GetMapping(value="/buy")
    public String buy(){
        return "fore/buy";
    }
    @GetMapping(value="/cart")
    public String cart(){
        return "fore/cart";
    }
    @GetMapping(value="/category")
    public String category(){
        return "fore/category";
    }
    @GetMapping(value="/confirmPay")
    public String confirmPay(){
        return "fore/confirmPay";
    }
    @GetMapping(value="/login")
    public String login(){
        return "fore/login";
    }
    @GetMapping(value="/orderConfirmed")
    public String orderConfirmed(){
        return "fore/orderConfirmed";
    }
    @GetMapping(value="/payed")
    public String payed(){
        return "fore/payed";
    }
    @GetMapping(value="/product")
    public String product(){
        return "fore/product";
    }
    @GetMapping(value="/registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }
    @GetMapping(value="/review")
    public String review(){
        return "fore/review";
    }
    @GetMapping(value="/search")
    public String searchResult(){
        return "fore/search";
    }
    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        //shiro判断
        if(subject.isAuthenticated())
            subject.logout();

//        //退出登录，session移除user对象
//        session.removeAttribute("user");
        return "redirect:home";
    }

}
