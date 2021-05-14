package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-28
 */
public interface PayLogService extends IService<PayLog> {
   // 生成微信支付二维码接口
    Map createNative(String orderNo);
}
