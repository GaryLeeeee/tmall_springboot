package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.domain.OrderItem;
import com.garylee.tmall_springboot.domain.User;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-29 13:58.
 */
public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    //当订单删除时订单设为"delete"，而不是删除该条数据
    String delete = "delete";
    void add(Order order);
    void delete(int id);
    void update(Order order);
    Order get(int id);
    List<Order> list();
    //"查看订单"功能，查询该用户下未删除的订单
    List<Order> listByUserWithoutDelete(User user);
    float add(Order order, List<OrderItem> orderItems);

}
