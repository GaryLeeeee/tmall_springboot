package com.garylee.tmall_springboot.Interceptor;

import com.garylee.tmall_springboot.domain.Category;
import com.garylee.tmall_springboot.domain.OrderItem;
import com.garylee.tmall_springboot.domain.User;
import com.garylee.tmall_springboot.service.CategoryService;
import com.garylee.tmall_springboot.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by GaryLee on 2018-12-03 00:58.
 */
public class OtherInterceptor implements HandlerInterceptor{
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        //将该用户所有购物车(即oid为null)的数量加起来赋值
        if(null!=user){
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for(OrderItem orderItem:orderItems)
                cartTotalItemNumber += orderItem.getNumber();
        }

        List<Category> categories = categoryService.listAll();
        String contextPath = request.getServletContext().getContextPath();
        //如果application.properties没设置server.context-path，则主页跳转到"/"
        if(contextPath.isEmpty())
            contextPath="/";
        //搜索框下面的几个分类信息
        request.getServletContext().setAttribute("categories_below_search",categories);
        //购物车订单项数量
        session.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
        //根目录(默认为"/",如果application.properties的server.context-path有设置即取那个)
        request.getServletContext().setAttribute("contextPath",contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
