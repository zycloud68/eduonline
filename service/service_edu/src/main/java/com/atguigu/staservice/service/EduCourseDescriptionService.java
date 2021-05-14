package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {
    // 根据课程id删除描述信息
    void removeCourseById(String courseId);
}
