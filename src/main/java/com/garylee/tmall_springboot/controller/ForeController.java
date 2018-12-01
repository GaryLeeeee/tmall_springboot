package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.comparator.*;
import com.garylee.tmall_springboot.domain.*;
import com.garylee.tmall_springboot.service.*;
import com.garylee.tmall_springboot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by GaryLee on 2018-11-13 09:00.
 */
@RestController
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @RequestMapping("forehome")
    public List<Category> forehome(){
        List<Category> categories = categoryService.listAll();
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }
    @PostMapping("foreregister")
    public Object register(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        //可将name转义,防止有人恶意注册,如 “<script>alert('papapa')</script> ”,导致打开网页就会弹窗
        //转义后则为“&lt;script&gt;alert(&#39;papapa&#39;)&lt;/script&gt;”
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if(exist){
            String message = "用户名已经被使用,不能使用";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }
    @PostMapping("forelogin")
    /**
    * @RequestBody: 可将请求体中的JSON字符串绑定到相应的bean上
     * 前端用ajax发起请求
     * 把name和password绑定到userParam中
    */
    public Object login(@RequestBody User userParam, HttpSession session){
        String name = userParam.getName();
        name = HtmlUtils.htmlEscape(name);
        User user = userService.getUser(name,userParam.getPassword());
        if(null!=user){
            //将用户登陆信息存放到session中，前端即可判断用户是否登陆，如果登陆即可获取其信息
            session.setAttribute("user",user);
            return Result.success();
        }else {
            String message = "账号或密码错误";
            return Result.fail(message);
        }
    }
    @RequestMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid")int pid){
        Product product = productService.get(pid);
        List<ProductImage> productSingleImages = productImageService.list(pid,ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(pid,ProductImageService.type_detail);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        //获取产品的所有属性值
        List<PropertyValue> pvs = propertyValueService.list(pid);
        //获取产品的所有评价
        List<Review> reviews = reviewService.list(pid);
        //设置产品的销量和评价量
        productService.setSaleAndReviewNumber(product);
//        productImageService.setFirstProdutImage(product);
        Map<String,Object> map = new HashMap<>();
        map.put("product",product);
        map.put("pvs",pvs);
        map.put("reviews",reviews);
        return Result.success(map);
    }
    @GetMapping("forecheckLogin")
    public Object checkLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("用户未登录");
    }
    @GetMapping("forecategory/{cid}")
    public Object foreCategory(@PathVariable("cid")int cid,String sort){
        Category category = categoryService.get(cid);
        //使category对象添加product属性
        productService.fill(category);
        //设置产品销量及评价
        productService.setSaleAndReviewNumber(category.getProducts());
        //通过比较器对已查询的products进行排序
        //好处在于不用多次查询数据库
        //如需多次查询数据库，可每次都order by查询后返回结果
        if(null!=sort){
            switch (sort){
                case "all":
                    Collections.sort(category.getProducts(), new ProductAllComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;
                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;
                case "review":
                    Collections.sort(category.getProducts(),new ProductReviewComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                    break;
            }
        }
        return category;
    }
}
