package com.atguigu.staservice.client;

import com.atguigu.commonutils.ResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class) // 调用服务名称
public interface VodClient {
    //定义调用的方法路径
    //根据视频id删除阿里云视频
    //@PathVariable注解一定要指定参数名称，否则出错
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public ResultResponse removeAlyVideo(@PathVariable("id") String id);

    // 删除多个阿里云视频的方法
    @DeleteMapping("delete-batch")
    public ResultResponse deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
