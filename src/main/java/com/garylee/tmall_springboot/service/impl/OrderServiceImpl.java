package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.OrderMapper;
import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.domain.OrderExample;
import com.garylee.tmall_springboot.service.OrderService;
import com.garylee.tmall_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by GaryLee on 2018-07-30 10:20.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        order.setUser(userService.get(order.getId()));//设置order的user
        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        for(Order order:orders) {
            order.setUser(userService.get(order.getId()));//设置orders的user
        }
        return orders;
    }
}
