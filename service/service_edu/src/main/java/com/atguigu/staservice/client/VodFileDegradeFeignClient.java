package com.atguigu.staservice.client;

import com.atguigu.commonutils.ResultResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public ResultResponse removeAlyVideo(String id) {
        return ResultResponse.error().message("视频删除失败,请重试");
    }

    @Override
    public ResultResponse deleteBatch(List<String> videoIdList) {
        return ResultResponse.error().message("多个视频删除失败,请重试");
    }
}
