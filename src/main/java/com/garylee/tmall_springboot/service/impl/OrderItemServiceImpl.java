package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.OrderItemMapper;
import com.garylee.tmall_springboot.domain.Order;
import com.garylee.tmall_springboot.domain.OrderItem;
import com.garylee.tmall_springboot.domain.OrderItemExample;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.service.OrderItemService;
import com.garylee.tmall_springboot.service.ProductService;
import com.garylee.tmall_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-29 17:42.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
//        setProduct(orderItems);
        return orderItems;
    }

    @Override
    /** 
    * @Description: 一个订单对应多个订单项，将它们设置到订单的实体类中
    * @Param: [order] 
    * @return: void 
    * @Date: 2018/07/30 10:19
    */ 
    public void fill(Order order) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(order.getId());
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);//在需要查看详细信息时才填充订单项(防止数据库压力过大)
        int totalNumber = 0;//数目
        float total = 0;//总金额
        for(OrderItem orderItem:orderItems){
            totalNumber += orderItem.getNumber();
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);

        //设置用户
        order.setUser(userService.get(order.getUid()));
    }

    @Override
    public void fill(List<Order> orders) {
        for(Order order:orders)
            fill(order);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);

        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        int saleCount = 0;
        for(OrderItem orderItem:orderItems)
            saleCount += orderItem.getNumber();
        return saleCount;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        //购物车的订单项,还没生成订单,所以oid为null
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);
        return orderItems;
    }

    public void setProduct(OrderItem orderItem){
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }

    public void setProduct(List<OrderItem> orderItems){
        for(OrderItem orderItem:orderItems)
            setProduct(orderItem);
    }
}
