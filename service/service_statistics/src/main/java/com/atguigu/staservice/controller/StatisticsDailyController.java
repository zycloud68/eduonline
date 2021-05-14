package com.atguigu.staservice.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-05-10
 */
@RestController
// @CrossOrigin 在配置了gateway之后,这个就不用写
@RequestMapping("/eduservice/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    //统计某一天注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public ResultResponse registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return ResultResponse.ok();
    }
    // 图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public ResultResponse showData(@PathVariable String type,@PathVariable String begin,
                                   @PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return ResultResponse.ok().data(map);
    }
}

