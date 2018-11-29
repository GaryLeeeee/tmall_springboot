package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.UserDao;
import com.garylee.tmall_springboot.dao.UserMapper;
import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.domain.UserExample;
import com.garylee.tmall_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaryLee on 2018-07-29 11:53.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDao userDao;
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        return userMapper.selectByExample(userExample);
    }

    @Override
    public boolean isExist(String name) {
        User user = userDao.findByName(name);
        return null!=user;
    }

    @Override
    public User getUser(String name, String password) {
        return userDao.getByNameAndPassword(name,password);
    }

}
