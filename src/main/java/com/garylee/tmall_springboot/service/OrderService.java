package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Order;

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
    String delete = "delete";
    void add(Order order);
    void delete(int id);
    void update(Order order);
    Order get(int id);
    List<Order> list();
}
