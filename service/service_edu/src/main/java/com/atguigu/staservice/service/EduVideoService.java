package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
public interface EduVideoService extends IService<EduVideo> {
   // 根据课程id删除小节信息
    void removeCourseById(String courseId);
}
