package com.garylee.tmall_springboot.test;

import com.garylee.tmall_springboot.controller.ProductController;
import com.garylee.tmall_springboot.domain.Product;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.service.ProductService;
import com.garylee.tmall_springboot.service.impl.ProductServiceImpl;
import com.garylee.tmall_springboot.util.FileUp;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Controller
public class Test {

    public static void main(String[] args) {
        ProductController productController = new ProductController();
        Product product = productController.get(90);
        System.out.println(product.getCategory());

    }

}
