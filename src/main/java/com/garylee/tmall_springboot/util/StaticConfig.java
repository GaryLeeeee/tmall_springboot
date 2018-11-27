package com.garylee.tmall_springboot.util;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by GaryLee on 2018-11-27 01:23.
 */
public class StaticConfig {
    public static String target = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/img";
    public static String root = new File("src\\main\\resources\\static\\img").getAbsolutePath();
    public static void main(String[] args) {
        System.out.println(target);
        System.out.println(root);
    }
}
