package com.atguigu.educms.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    /**
     * 前台用户查询所有的轮播图
     * @return
     */
    @ApiOperation(value="前台用户查询所有的轮播图")
    @GetMapping("/getAllBanner")
    public ResultResponse getAllBanner(){
       List<CrmBanner> list =  bannerService.selectAllBanner();
       return ResultResponse.ok().data("list",list);
    }

}

