package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GaryLee on 2018-07-29 12:28.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("admin_user_list")
    public String list(){
        return "admin/listUser";
    }
    @RequestMapping("listUser")
    @ResponseBody
    public PageInfo getAll(@RequestParam(value = "start",defaultValue = "0")int start, @RequestParam(value = "size",defaultValue = "5")int size){
        PageHelper.startPage(start,size);
        PageInfo<User> page = new PageInfo<>(userService.list());
        return page;
    }
}
