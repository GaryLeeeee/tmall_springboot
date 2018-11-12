package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.service.OrderItemService;
import com.garylee.tmall_springboot.service.OrderService;
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
 * Created by GaryLee on 2018-07-30 10:25.
 */
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @RequestMapping("admin_order_list")
    public String list(){
        return "admin/listOrder";
    }
    @RequestMapping("listOrder")
    @ResponseBody
    public PageInfo getAll(@RequestParam(value = "start",defaultValue = "0")int start, @RequestParam(value = "size",defaultValue = "5")int size){
        PageHelper.startPage(start,size);
        List<Order> orders = orderService.list();
        orderItemService.fill(orders);
        PageInfo<Order> page = new PageInfo<>(orders);
        return page;
    }
    @RequestMapping("admin_order_delivery")
    public String delivery(int id){
        Order order = orderService.get(id);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
