package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.OrderMapper;
import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.domain.OrderExample;
import com.garylee.tmall_springboot.domain.OrderItem;
import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.service.OrderItemService;
import com.garylee.tmall_springboot.service.OrderService;
import com.garylee.tmall_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    OrderItemService orderItemService;
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

    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(user.getId()).andStatusNotEqualTo(OrderService.delete);
        example.setOrderByClause("id desc");
        //每个订单添加订单项方便显示
        List<Order> orders = orderMapper.selectByExample(example);;
        orderItemService.fill(orders);
        return orders;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    //返回订单总价
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        //这时候order才开始存到数据库,自动生成id
        add(order);

        if(false)
            throw new RuntimeException();

        for(OrderItem orderItem:orderItems){
            //为提交订单的订单项设置oid
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);

            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return total;
    }
}
