package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.ResultResponse;
import com.atguigu.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    // 生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public ResultResponse createOrder(@PathVariable String courseId, HttpServletRequest request){
        // 1.首先我们应在token中获取视频id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        // 2. 根据获取的信息添加订单信息
        String orderNo = orderService.createOrders(courseId,memberIdByJwtToken);
        return ResultResponse.ok().data("orderId",orderNo);
    }
}

