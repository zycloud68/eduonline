package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.EduCourseDescription;
import com.atguigu.staservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.staservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeCourseById(String courseId) {
        QueryWrapper<EduCourseDescription> wrapper  = new QueryWrapper<>();
        wrapper.eq("id",courseId);
        baseMapper.delete(wrapper);
    }
}
