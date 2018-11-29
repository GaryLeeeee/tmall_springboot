package com.garylee.tmall_springboot.service;

import com.garylee.tmall_springboot.domain.Review;

import java.util.List;

/**
 * Created by GaryLee on 2018-11-29 15:44.
 */
public interface ReviewService {
    void add(Review review);
    void delete(int id);
    void update(Review review);
    Review get(int id);
    List<Review> list(int pid);
    int getCount(int pid);
}
