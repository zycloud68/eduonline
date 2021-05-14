package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-28
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    // 生成二维码支付接口
    @GetMapping("/createNative/{orderNo}")
    public ResultResponse createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return ResultResponse.ok().data(map);
    }

}

