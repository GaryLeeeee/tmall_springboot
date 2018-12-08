package com.garylee.tmall_springboot.controller;

import com.garylee.tmall_springboot.comparator.*;
import com.garylee.tmall_springboot.dao.UserDao;
import com.garylee.tmall_springboot.domain.*;
import com.garylee.tmall_springboot.service.*;
import com.garylee.tmall_springboot.util.Result;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
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
    @Autowired
    OrderService orderService;
    @Autowired
    UserDao userDao;
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

        //shiro
        //盐,随机生成,用于与已有字符串拼接
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //加密次数(即加密后的字符串继续加密)
        int times = 2;
        //加密算法
        String algorithmName = "md5";
        //生成加密码
        String encodedPassword = new SimpleHash(algorithmName,password,salt,times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);

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

        //shiro
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //token传递输入的账号和密码
        UsernamePasswordToken token = new UsernamePasswordToken(name,userParam.getPassword());
        try {
            //登陆操作,没报错的话则登陆成功
            subject.login(token);
            //根据name到数据库获取对应的user
            User user = userDao.findByName(name);
            //没catch到错误说明
            session.setAttribute("user",user);
            return Result.success();
        }catch (AuthenticationException e){
            String message = "账号或密码错误";
            return Result.fail(message);
        }

//        User user = userService.getUser(name,userParam.getPassword());
//        if(null!=user){
//            //将用户登陆信息存放到session中，前端即可判断用户是否登陆，如果登陆即可获取其信息
//            session.setAttribute("user",user);
//            return Result.success();
//        }else {
//            String message = "账号或密码错误";
//            return Result.fail(message);
//        }
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
    //前台判断是否登陆+shiro
    @GetMapping("forecheckLogin")
    public Object checkLogin(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())

//        User user = (User) session.getAttribute("user");
//        if(null!=user)
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
    //立即购买的时候只有一个oiid，购物车多选传过去的事多个oiid，兼容
    public Object buy(int[] oiid,HttpSession session){
        float total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for(int id:oiid){
            OrderItem orderItem = orderItemService.get(id);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            orderItems.add(orderItem);
        }
        //设置结算页面内的订单项
        session.setAttribute("ois",orderItems);

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("orderItems",orderItems);
        return Result.success(map);
    }
    //"立即购买"和"添加购物车"共同方法
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
    //修改购物车的产品数量
    @GetMapping("forechangeOrderItem")
    public Object changOrderItem(int pid,int num,HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,不能修改订单数量!");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for(OrderItem orderItem:orderItems){
            if(orderItem.getPid()==pid){
                orderItem.setNumber(num);
                orderItemService.update(orderItem);
                break;
            }
        }
        return Result.success();
    }
    //删除购物车的订单项
    //前端只是暂时隐藏了该订单项并删除数据库,可通过删除数据库后重新遍历
    @GetMapping("foredeleteOrderItem")
    public Object deleteOrderItem(int oiid,HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,不能删除订单!");
//        orderItemService.delete(oiid);
        for(OrderItem orderItem:orderItemService.listByUser(user.getId())){
            if(orderItem.getId()==oiid) {
                orderItemService.delete(oiid);
                break;
            }
        }
        return Result.success();
    }
    // TODO: 2018/12/4 0004 生成订单并没有删除库存
    @PostMapping("forecreateOrder")
    //前端vue有个order,直接传给后端处理
    //order传过来时已有字段:address,post,receiver,moblie,userMessage
    public Object createOrder(@RequestBody Order order,HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,不能生成订单!");
        //根据当前时间加上4位随机数生成订单号(如201804240905441389018)
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        //订单刚完成时,设置为未付款
        order.setStatus(OrderService.waitPay);
        order.setUid(user.getId());
        order.setCreateDate(new Date());
        //从session中获取订单项集合，在结算功能的buy，订单项集合被放到了session中
        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
        //此前order未存到数据库,无id
        float total = orderService.add(order,orderItems);

        Map<String,Object> map = new HashMap<>();
        //标记order的id方便付款...
        map.put("oid",order.getId());
        //用户在付款界面显示付款总额
        map.put("total",total);

        return Result.success(map);
    }
    //支付功能
    // TODO: 2018/12/4 0004  该系统目前浏览器后退，继续点"确认支付"，将会覆盖原有order，并更新支付时间
    @GetMapping("forepayed")
    public Object payed(int oid){
        Order order = orderService.get(oid);
        //设置支付日期payDate
        order.setPayDate(new Date());
        //状态设置为待发货
        order.setStatus(OrderService.waitDelivery);
        //更新order信息
        orderService.update(order);

        return order;
    }
    @GetMapping("forebought")
    public Object bought(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,不能进行查看订单操作!");
        List<Order> orders = orderService.listByUserWithoutDelete(user);

        return orders;
    }
    //"付款"功能
    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,无法获取订单信息!");
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        orderItemService.fill(order);
        return order;
    }
    //"确认收货"功能
    @GetMapping("foreorderConfirmed")
    public Object confirmed(int oid,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null)
            return Result.fail("未登录,不能确认收获!");
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);

        return Result.success();
    }
    //put是什么鬼
    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        //已经添加拦截器,所以可以不用进行user的null判断
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return Result.success();
    }
    @GetMapping("forereview")
    public Object review(int oid){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        List<OrderItem> orderItems = order.getOrderItems();
        
        //获取订单的第一个产品
        // TODO: 2018/12/4 0004 目前包含多个产品的订单，也只可以评价第一个产品
        Product product = orderItems.get(0).getProduct();
        //设置销量和评价
        productService.setSaleAndReviewNumber(product);

        //获取该产品的所有评价
        List<Review> reviews = reviewService.list(product.getId());
        
        Map<String,Object> map = new HashMap<>();
        map.put("o",order);
        map.put("p",product);
        map.put("reviews",reviews);

        return Result.success(map);
    }

    // TODO: 2018/12/4 0004 该页刷新后可以继续评价，并可以继续add一个新的review
    @PostMapping("foredoreview")
    public Object doreview(int oid,String content,int pid,HttpSession session){
        //更新订单信息"waitReview"->"finish"
        Order order = orderService.get(oid);
        //解决一个todo!
        //不判断为"finish"，是防止有其他状态的订单进入
        if(!order.getStatus().equals(OrderService.waitReview))
            return Result.fail("用户已经评价过此订单(或为无效订单)!");
        order.setStatus(OrderService.finish);
        orderService.update(order);

        //添加review表数据
        Review review = new Review();
        review.setPid(pid);
        content = HtmlUtils.htmlEscape(content);
        review.setContent(content);
        review.setCreateDate(new Date());

        //设置用户
//        review.setUid(order.getUid());
        User user = (User) session.getAttribute("user");
        review.setUid(user.getId());
        reviewService.add(review);

        return Result.success();
    }
}
