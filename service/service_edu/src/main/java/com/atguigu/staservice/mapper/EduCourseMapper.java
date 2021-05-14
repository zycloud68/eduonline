package com.atguigu.staservice.mapper;

import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.frontVo.CourseWebVo;
import com.atguigu.staservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

  public CoursePublishVo getPublishCourseInfo(String courseId);

  // 根据课程id查询课程详细信息
    CourseWebVo getBaseCourseInfo(@Param("courseId") String courseId);
}
