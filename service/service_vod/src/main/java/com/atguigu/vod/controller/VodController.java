package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.ResultResponse;
import com.atguigu.handler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传视频文件
     * @param file
     * @return
     */
    @PostMapping("uploadAliVideo")
    public ResultResponse uploadAliVideo(MultipartFile file){
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return ResultResponse.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除阿里云视频
     * @param id
     * @return
     */
    @DeleteMapping("removeAlyVideo/{id}")
    public ResultResponse removeAlyVideo(@PathVariable String id){
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request中设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法来删除对象
            client.getAcsResponse(request);
            return ResultResponse.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw  new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 一个课程里面有很多章节,一个章节里面有多个小节,小节里面有多个视频
     * @param videoIdList
     * @return
     */
    // 删除多个阿里云视频的方法
    @DeleteMapping("delete-batch")
    public ResultResponse deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return ResultResponse.ok();
    }

    // 根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public ResultResponse getPlayAuth(@PathVariable String id){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return ResultResponse.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}

