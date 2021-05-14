package com.atguigu.educms.controller;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * @param page 当前页数
     * @param limit 当前页数显示的内容
     * @return
     */
    // 1. 分页查询banner
    @ApiOperation(value = "分页查询Banner")
    @GetMapping("pageBanner/{page}/{limit}")
    public ResultResponse pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> pageBanner= new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return ResultResponse.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    /**
     * 增添Banner信息
     * @param crmBanner Banner实体类
     * @return
     */
    // 2. 添加banner
    @ApiOperation(value = "添加Banner")
    @PostMapping("addBanner")
    public ResultResponse addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return ResultResponse.ok();
    }

    /**
     * 获取banner的id,根据id来修改banner的信息
     * @param id
     * @return
     */
    // 3. 修改banner
    @ApiOperation(value = "获取Bannerid")
    @GetMapping("/get/{id}")
    public ResultResponse getBannerId(@PathVariable String id){
        CrmBanner banner = bannerService.getById(id);
        return ResultResponse.ok().data("item",banner);
    }

    @ApiOperation(value = "根据获取Bannerid来修改信息")
    // 首先获取banner的id,根据id来修改banner的信息
    @PostMapping("/update")
    public ResultResponse updateBanner(@RequestBody CrmBanner crmBanner){
        boolean update = bannerService.updateById(crmBanner);
        if (update){
            return ResultResponse.ok().message("修改轮播图成功");
        }else{
            return ResultResponse.error().message("修改轮播图成功,请重试");
        }
    }

    /**
     * 删除轮播图
     * @param id  轮播图id
     * @return
     */
    // 4. 删除banner
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public ResultResponse removeBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return ResultResponse.ok();
    }
}
