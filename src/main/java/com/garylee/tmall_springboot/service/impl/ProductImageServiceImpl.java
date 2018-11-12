package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.ProductImageMapper;
import com.garylee.tmall_springboot.domain.ProductImage;
import com.garylee.tmall_springboot.domain.ProductImageExample;
import com.garylee.tmall_springboot.service.ProductImageService;
import com.garylee.tmall_springboot.util.FileUp;
import com.garylee.tmall_springboot.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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
        File imgFolder_small = null;
        File imgFolder_middle = null;
        String bucket = null;//定义当前为哪个类型图片
        //判断为single还是detail图片
        if(ProductImageService.type_single.equals(productImage.getType())){
            imgFolder = new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\product-single");
            imgFolder_small = new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\product-single\\small");
            imgFolder_middle = new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\product-single\\middle");
            bucket = "product-single";
        }else if(ProductImageService.type_detail.equals(productImage.getType())){
            imgFolder = new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\product-detail");
            bucket = "product-detail";
        }
        String fileName = productImage.getId()+".jpg";
        File file = new File(imgFolder,fileName);

        //删除云上对应的已有资源(如果存在)
        if(file.exists())
            fileUp.delete(productImage.getId()+".jpg",bucket);
        if(!file.getParentFile().exists())
            file.mkdirs();


        try{
            multipartFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);

            //上传到七牛云(以用户名id为名--single/detail)
            fileUp.uploaded(productImage.getId()+".jpg",bucket);

            //如果是single则设置图片大小存储
            if(ProductImageService.type_single.equals(productImage.getType())){
                File file_small = new File(imgFolder_small,fileName);
                File file_middle = new File(imgFolder_middle,fileName);

                //删除云上对应的已有资源(如果存在)
                if(file_small.exists())
                    fileUp.delete("small/" + productImage.getId() + ".jpg", "product-single");
                if(file_middle.exists())
                    fileUp.delete("middle/" + productImage.getId() + ".jpg", "product-single");
                if(!file_small.getParentFile().exists())
                    file_small.mkdirs();
                if(!file_middle.getParentFile().exists())
                    file_middle.mkdirs();

                ImageUtil.resizeImage(file,56,56,file_small);
                ImageUtil.resizeImage(file,217,190,file_middle);

                //上传到七牛云(以用户名id为名--single/detail)
                fileUp.uploaded("small/" +productImage.getId()+".jpg","product-single");
                fileUp.uploaded("middle/" +productImage.getId()+".jpg","product-single");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
