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
}
