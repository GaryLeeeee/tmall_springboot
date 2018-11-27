package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.ProductImageMapper;
import com.garylee.tmall_springboot.domain.ProductImage;
import com.garylee.tmall_springboot.domain.ProductImageExample;
import com.garylee.tmall_springboot.service.ProductImageService;
import com.garylee.tmall_springboot.util.FileUp;
import com.garylee.tmall_springboot.util.ImageUtil;
import com.garylee.tmall_springboot.util.StaticConfig;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by GaryLee on 2018-07-16 13:49.
 */
@Service
public class ProductImageServiceImpl implements ProductImageService{
    @Autowired
    ProductImageMapper productImageMapper;
    @Autowired
    FileUp fileUp;
    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    @Override
    public ProductImage get(int id) {
        ProductImage productImage = productImageMapper.selectByPrimaryKey(id);
        return productImage;
    }

    @Override
    public List<ProductImage> list(int pid,String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id desc");
        return productImageMapper.selectByExample(productImageExample);
    }

    @Override
    public List<ProductImage> list(int pid) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid);
        productImageExample.setOrderByClause("id desc");
        return productImageMapper.selectByExample(productImageExample);
    }

    @Override
    //保存到本地，并上传到七牛云
    //判断是否为single或detail图片
    public void up(MultipartFile multipartFile, ProductImage productImage) {
        //        File imgFolder = new File("src\\main\\resources\\static\\img\\category");
        File imgFolder = null;
        File imgFolderTarget = null;
        File imgFolder_small = null;
        File imgFolder_smallTarget = null;
        File imgFolder_middle = null;
        File imgFolder_middleTarget = null;
        String bucket = null;//定义当前为哪个类型图片
        //判断为single还是detail图片
        if(ProductImageService.type_single.equals(productImage.getType())){
            imgFolder = new File(StaticConfig.root+"\\productSingle");
            imgFolderTarget = new File(StaticConfig.target+"\\productSingle");
            imgFolder_small = new File(StaticConfig.root+"\\productSingle_small");
            imgFolder_smallTarget = new File(StaticConfig.target+"\\productSingle_small");
            imgFolder_middle = new File(StaticConfig.root+"\\productSingle_middle");
            imgFolder_middleTarget = new File(StaticConfig.target+"\\productSingle_middle");


            bucket = "product-single";
        }else if(ProductImageService.type_detail.equals(productImage.getType())){
            imgFolder = new File(StaticConfig.root+"\\productDetail");
            imgFolderTarget = new File(StaticConfig.target+"\\productDetail");

            bucket = "product-detail";
        }
        String fileName = productImage.getId()+".jpg";
        File file = new File(imgFolder,fileName);
        File file2 = new File(imgFolderTarget,fileName);

        /*
        七牛回收，暂时去掉
        //删除云上对应的已有资源(如果存在)
        if(file.exists())
            fileUp.delete(productImage.getId()+".jpg",bucket);
        */
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.exists());
//        System.out.println(file2.getAbsolutePath());
//        System.out.println(file2.exists());
        try{
            //templates
            multipartFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
            //target,直接复制过去得了
            Files.copy(file.toPath(),file2.toPath());

            //上传到七牛云(以用户名id为名--single/detail)---七牛回收，暂时去掉
//            fileUp.uploaded(productImage.getId()+".jpg",bucket);

            //如果是single则设置图片大小存储
            if(ProductImageService.type_single.equals(productImage.getType())){
                File file_small = new File(imgFolder_small,fileName);
                File file_middle = new File(imgFolder_middle,fileName);
                /*
                七牛回收，暂时去掉
                //删除云上对应的已有资源(如果存在)
                if(file_small.exists())
                    fileUp.delete("small/" + productImage.getId() + ".jpg", "product-single");
                if(file_middle.exists())
                    fileUp.delete("middle/" + productImage.getId() + ".jpg", "product-single");
                */
                // TODO: 2018/11/26 0026 做到这 
                ImageUtil.resizeImage(file,56,56,file_small);
                ImageUtil.resizeImage(file,217,190,file_middle);

                /*
                七牛回收，暂时去掉
                //上传到七牛云(以用户名id为名--single/detail)
                fileUp.uploaded("small/" +productImage.getId()+".jpg","product-single");
                fileUp.uploaded("middle/" +productImage.getId()+".jpg","product-single");
                */

                //target
                File file_smallTarget = new File(imgFolder_smallTarget,fileName);
                File file_middleTarget = new File(imgFolder_middleTarget,fileName);
                Files.copy(file_small.toPath(),file_smallTarget.toPath());
                Files.copy(file_middle.toPath(),file_middleTarget.toPath());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
