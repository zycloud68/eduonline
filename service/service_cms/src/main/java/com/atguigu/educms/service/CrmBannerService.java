package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-16
 */
public interface CrmBannerService extends IService<CrmBanner> {
   // 前台用户查询所有的轮播图
    List<CrmBanner> selectAllBanner();

}
