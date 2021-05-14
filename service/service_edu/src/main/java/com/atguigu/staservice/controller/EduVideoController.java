package com.atguigu.staservice.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.client.VodClient;
import com.atguigu.staservice.entity.EduVideo;
import com.atguigu.staservice.service.EduVideoService;
import com.atguigu.handler.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-01
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    /**
     * 增加课程小节内容
     * @param eduVideo
     * @return
     */
    @ApiOperation(value = "增加课程小节内容")
    @PostMapping("addVideo")
    public ResultResponse addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return ResultResponse.ok();
    }

    /**
     * 应当先删除视频,再删除小节内容,执行方法不能更改
     * 删除课程小节内容
     * @param id
     * @return
     */
    @ApiOperation(value = "删除课程小节内容")
    @DeleteMapping("{id}")
    public ResultResponse deleteVideo(@PathVariable String id){
        // 1. 先根据小节id来查询视频id,调用方法来删除视频
        EduVideo eduVideo = videoService.getById(id);
        // 2. 根据eduVideo来查找视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        // 2.1 判断videoSourceId是否为空
        if (!StringUtils.isEmpty(videoSourceId)){
            ResultResponse result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new GuliException(20001,"删除视频失效,触发熔断器工作");
            }
        }
        // 3. 删除小节内容
        boolean flag = videoService.removeById(id);
        if (flag){
            return ResultResponse.ok();
        } else {
            return ResultResponse.error();
        }
    }
}

