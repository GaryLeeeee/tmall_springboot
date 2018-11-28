package com.garylee.tmall_springboot.dao;

import com.garylee.tmall_springboot.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from user where name = #{name}")
    User findByName(String name);
}
