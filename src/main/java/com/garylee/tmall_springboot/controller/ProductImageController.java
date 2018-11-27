package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.domain.ProductImage;
import com.garylee.tmall_springboot.domain.Property;
import com.garylee.tmall_springboot.service.ProductImageService;
import com.garylee.tmall_springboot.util.FileUp;
import com.garylee.tmall_springboot.util.StaticConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by GaryLee on 2018-07-16 13:58.
 */
@Controller
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    FileUp fileUp;
    @RequestMapping("admin_productImage_list")
    public String list(){
        return "admin/listProductImage";
    }
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, @RequestParam("image") MultipartFile multipartFile){
        //添加到数据库
        //注意需要添加数据库后将id设置到对象上去
        productImageService.add(productImage);
        //保存本地、上传文件
        productImageService.up(multipartFile,productImage);
        return "redirect:/admin_productImage_list?pid="+productImage.getPid();
    }
    @RequestMapping("admin_productImage_delete")
    public String delete(int id){
        //sql
        ProductImage productImage = productImageService.get(id);
//        productImageService.delete(id);//放到最后操作
        //file

        if(ProductImageService.type_single.equals(productImage.getType())) {
            /*
            七牛回收，暂时去掉
            //删除七牛空间中的文件
            fileUp.delete(id + ".jpg", "product-single");
            System.out.println(id+".jpg"+"已删除!");
            fileUp.delete("small/" + id + ".jpg", "product-single");
            System.out.println("small/"+id+".jpg"+"已删除!");
            fileUp.delete("middle/" + id + ".jpg", "product-single");
            System.out.println("middle/"+id+".jpg"+"已删除!");
            */
            //删除本地文件
            File imgFolder = new File(StaticConfig.root+"\\productSingle");
            File imgFolderTarget = new File(StaticConfig.target+"\\productSingle");
            File imgFolder_small = new File(StaticConfig.root+"\\productSingle_small");
            File imgFolder_smallTarget = new File(StaticConfig.target+"\\productSingle_small");
            File imgFolder_middle = new File(StaticConfig.root+"\\productSingle_middle");
            File imgFolder_middleTarget = new File(StaticConfig.target+"\\productSingle_middle");
            //root
            File file = new File(imgFolder, id + ".jpg");
            File file_small = new File(imgFolder_small, id + ".jpg");
            File file_middle = new File(imgFolder_middle, id + ".jpg");
            file.delete();
            file_small.delete();
            file_middle.delete();
            //target
            File file2 = new File(imgFolderTarget, id + ".jpg");
            File file_small2 = new File(imgFolder_smallTarget, id + ".jpg");
            File file_middle2 = new File(imgFolder_middleTarget, id + ".jpg");
            file2.delete();
            file_small2.delete();
            file_middle2.delete();
        }else if(ProductImageService.type_detail.equals(productImage.getType())){
            File imgFolder = new File(StaticConfig.root+"\\productDetail");
            File imgFolderTarget = new File(StaticConfig.target+"\\productDetail");
            //删除七牛空间中的文件,暂时去掉
//            fileUp.delete(id + ".jpg", "product-detail");
            //删除本地文件
            File file = new File(imgFolder,id+".jpg");
            file.delete();
            File file2 = new File(imgFolderTarget,id+".jpg");
            file2.delete();
        }

        productImageService.delete(id);

        return "redirect:/admin_productImage_list?pid="+productImage.getPid();
    }
    @RequestMapping("listProductImage")
    @ResponseBody
    public List<ProductImage> getAll(@RequestParam("pid") int pid){
        return productImageService.list(pid);
    }
    @RequestMapping("getProductImage")
    @ResponseBody
    public ProductImage get(int id){
        return productImageService.get(id);
    }
}
