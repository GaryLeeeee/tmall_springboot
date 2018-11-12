package com.garylee.tmall_springboot.util;

import com.garylee.tmall_springboot.domain.Category;
import com.google.gson.Gson;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class FileUp {
    //上传到七牛云存储(以id命名文件)
    //定义存储空间名
    //id修改为全称string
    public void uploaded(String key,String bucket) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "1ts404fHlylChWoeiYP-JgJppYje0m7OYe-eklvk";
        String secretKey = "Jxj5iXqFMxpJ6n337zjUW6k7gqVVj79_T3Ttdypd";
        //存储空间名
//        String bucket = "category";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = new File("src\\main\\resources\\static\\img\\category\\"+id+".jpg").getAbsolutePath();
//        String localFilePath = "d:\\Users\\Administrator\\Desktop\\tmall_image\\category\\" + id + ".jpg";
        String localFilePath = "d:\\Users\\Administrator\\Desktop\\tmall_image\\"+bucket+"\\" + key;
        System.out.println("path:"+localFilePath);
        System.out.println("exist?:"+new File(localFilePath).exists());
        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = id + ".jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //判断空间是否存在，以及删除操作
        if(new File(localFilePath).exists()) {
            upToken = auth.uploadToken(bucket,key);
        }
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key+"上传成功");
//            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
    //删除空间中的文件操作
    public void delete(String fileName,String bucket) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "1ts404fHlylChWoeiYP-JgJppYje0m7OYe-eklvk";
        String secretKey = "Jxj5iXqFMxpJ6n337zjUW6k7gqVVj79_T3Ttdypd";
//        String bucket = "category";
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    public void refresh(int id){
        String accessKey = "1ts404fHlylChWoeiYP-JgJppYje0m7OYe-eklvk";
        String secretKey = "Jxj5iXqFMxpJ6n337zjUW6k7gqVVj79_T3Ttdypd";
        Auth auth = Auth.create(accessKey, secretKey);
        CdnManager c = new CdnManager(auth);
        //待刷新的链接列表
        String[] urls = new String[]{
                "http://p8iu5y6va.bkt.clouddn.com/",
        };
        try {
            //单次方法调用刷新的链接不可以超过100个
            CdnResult.PrefetchResult result = c.prefetchUrls(urls);
            System.out.println(result.code);
            //获取其他的回复内容
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }
}
