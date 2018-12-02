package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.domain.OrderItem;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-29 16:49.
 */
public interface OrderItemService {
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    OrderItem get(int id);
    List<OrderItem> list();

    void fill(Order order);
    void fill(List<Order> orders);
    //根据产品获取销售量
    int getSaleCount(int pid);
    //查询user购物车的订单项(用于判断用户的订单项有某个产品，有的话购买则在原有订单项上update，否则新建）
    List<OrderItem> listByUser(int uid);
}
