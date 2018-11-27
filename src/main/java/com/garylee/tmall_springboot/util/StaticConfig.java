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
        System.out.println(new File("D:\\Study\\idea\\workplace\\tmall_springboot\\src\\main\\resources\\static\\img\\productSingle\\1.jpg").exists());
        System.out.println(new File("D:\\Study\\idea\\workplace\\tmall_springboot\\target\\classes\\static\\img\\productSingle\\1.jpg").exists());
        File file = new File("d:\\Users\\Administrator\\Desktop\\detail.jpg");
        File file2 = new File("d:\\Users\\Administrator\\Desktop\\details.jpg");

        try {
            Files.copy(file.toPath(),file2.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
