package com.garylee.tmall_springboot.test;

import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.util.FileUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
public class Test {
    @Autowired
    CategoryService categoryService;
    @Autowired
    FileUp fileUp;
@RequestMapping("delete")
@ResponseBody
public void delete(){
    File imageFolder= new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\category");
    File file = new File(imageFolder,84+".jpg");
    fileUp.delete(84+".jpg");

}
    @RequestMapping("add")
    @ResponseBody
    public void add(){
        fileUp.uploaded(84);
    }
    public static void main(String[] args) {
        File imageFolder= new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\category");
        File file = new File(imageFolder,0+".jpg");

    }


    @RequestMapping("ppp")
    public String o(){
        return "admin/vue";
    }
}
