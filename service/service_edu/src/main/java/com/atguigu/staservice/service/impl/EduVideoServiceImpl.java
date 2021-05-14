package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.EduVideo;
import com.atguigu.staservice.mapper.EduVideoMapper;
import com.atguigu.staservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * TODO 删除对应的视频内容
     * 根据课程id来删除小节信息
     * @param courseId 课程id
     */
    @Override
    public void removeCourseById(String courseId) {
        QueryWrapper<EduVideo> wrapper  = new QueryWrapper<>();
         wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
