package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.ReviewMapper;
import com.garylee.tmall_springboot.dao.UserDao;
import com.garylee.tmall_springboot.dao.UserMapper;
import com.garylee.tmall_springboot.domain.Review;
import com.garylee.tmall_springboot.domain.ReviewExample;
import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.service.ReviewService;
import com.garylee.tmall_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaryLee on 2018-11-29 15:44.
 */
@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserService userService;
    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> reviews = reviewMapper.selectByExample(example);
        //为每个订单附加用户信息(已知用户id)
        setUser(reviews);
        return reviews;
    }
    public void setUser(Review review){
        User user = userService.get(review.getUid());
        review.setUser(user);
    }
    public void setUser(List<Review> reviews){
        for(Review review:reviews)
            setUser(review);
    }
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
