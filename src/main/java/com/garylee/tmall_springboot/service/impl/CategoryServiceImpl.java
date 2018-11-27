package com.garylee.tmall_springboot.service.impl;

import com.garylee.tmall_springboot.dao.CategoryMapper;
import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.CategoryExample;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.util.FileUp;
import com.garylee.tmall_springboot.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    FileUp fileUp;

    @Override
    public List<Category> listAll() {
//        return categoryMapper.listAll();
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }

    @Override
    public int total() {
        CategoryExample categoryExample = new CategoryExample();
        return (int) categoryMapper.countByExample(categoryExample);
    }

    @Override
    public void add(Category category) {
//        categoryMapper.add(category);
        categoryMapper.insert(category);
    }
//    保存到本地，并上传到七牛云
// TODO: 2018/7/17 0017 待看懂原理
    public void up(MultipartFile multipartFile,Category category) {
//        File imgFolder = new File("D:\\Study\\idea\\workplace\\tmall_springboot\\src\\main\\resources\\static\\img\\category");
        File imgFolder = null;
        try {
            // TODO: 2018/11/26 0026 只弄了category的target设置，后续加其他实体类和templates中的
            imgFolder = new File(ResourceUtils.getFile("classpath:").getPath()+"\\static\\img\\category");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        File imgFolder = new File("d:\\Users\\Administrator\\Desktop\\tmall_image\\category");
        System.out.println("filePath:"+imgFolder.getAbsolutePath());
        System.out.println("id:"+category.getId());
        File file = new File(imgFolder,category.getId()+".jpg");
        /*
        七牛云回收，暂时去掉
        //删除云上对应的已有资源(如果存在)
        if(file.exists())
            fileUp.delete(category.getId()+".jpg","category");
            */
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            multipartFile.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*
        七牛云回收，暂时去掉
        //上传到七牛云(以用户名id为名)
        fileUp.uploaded(category.getId()+".jpg","category");
        */
    }

    @Override
    public void delete(int id) {
//        categoryMapper.delete(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
//        return categoryMapper.get(id);
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
//        categoryMapper.update(category);
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
