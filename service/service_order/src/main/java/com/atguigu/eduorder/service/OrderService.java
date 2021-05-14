package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-28
 */
public interface OrderService extends IService<Order> {
    // 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);
}
