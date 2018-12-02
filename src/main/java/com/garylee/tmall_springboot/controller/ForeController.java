package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.comparator.*;
import com.garylee.tmall_springboot.domain.*;
import com.garylee.tmall_springboot.service.*;
import com.garylee.tmall_springboot.util.Result;
import org.apache.ibatis.annotations.Param;
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
    @Autowired
    OrderItemService orderItemService;
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
    @PostMapping("foresearch")
    public Object search(String keyword){
        List<Product> products = productService.search(keyword);
        return products;
    }
    @GetMapping("forebuyone")
    /**
     * @description："立即购买"按钮，将产品信息和用户信息存放到orderItem中存入数据库
     * @pid: 产品id
     * @num：产品数量
     * @session：用来确定当前登陆的用户
     * @return:ooid
     * @Date: 2018/12/02 10:25
    */
    public Object buyone(int pid,int num,HttpSession session){
        return buyoneAndCart(pid,num,session);
    }
    @GetMapping("forebuy")
    //自己写的方法，不一定无bug
    //判断如果有id则为购买某一产品，如果无则为查看该用户下购物车
    public Object buy(int oiid,HttpSession session){
        float total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        //如果没登录，则返回错误给前端处理跳转
        User user = (User) session.getAttribute("user");

        if(null==user)
            return Result.fail("没有用户登陆信息!");
        //判断如果orderitem的id与uid是否匹配
        if(orderItemService.get(oiid).getUid()!=user.getId())
            return Result.fail("该用户查无此订单!");

        //如果有ooid,即某个产品"立即购买"
            OrderItem orderItem = orderItemService.get(oiid);
            //总价
            total += orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
            orderItems.add(orderItem);


        session.setAttribute("ois",orderItems);

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("orderItems",orderItems);
        return Result.success(map);
    }
    private int buyoneAndCart(int pid,int num,HttpSession session){
        User user = (User) session.getAttribute("user");
        //判断该用户是否已将该产品加到订单项(购物车)
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        //判断是否有已存在uid+oiid的订单项
        boolean exist = false;
        //存放orderItem的id并作为return结果
        int oiid = -1;
        for(OrderItem orderItem:orderItems){
            if(pid==orderItem.getPid()){
                orderItem.setNumber(orderItem.getNumber()+num);
                orderItemService.update(orderItem);
                exist = true;
                oiid = orderItem.getId();
                break;
            }

        }
        //如果无此记录，则新添加一条orderItem
        if(!exist){
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setPid(pid);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            //必须放入数据库后才有id
            oiid = orderItem.getId();
        }
        return oiid;
    }
    @GetMapping("foreaddCart")
    public Object addCart(int pid,int num,HttpSession session){
        //只是添加到购物车，不用跳转结算页面
        buyoneAndCart(pid,num,session);
        return Result.success();
    }
    @GetMapping("forecart")
    public Object cart(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        return orderItems;
    }
}
