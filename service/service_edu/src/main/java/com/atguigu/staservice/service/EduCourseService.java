package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.frontVo.CourseFrontVo;
import com.atguigu.staservice.entity.frontVo.CourseWebVo;
import com.atguigu.staservice.entity.vo.CourseInfoVo;
import com.atguigu.staservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
    // 根据课程id来查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    // 根据课程id来删除课程信息
    void removeCourse(String courseId);

    // 查询前台页面前8条热门课程
    List<EduCourse> selectCourses();

    // 条件查询带分页的查询课程
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    // 根据课程id查询课程详细信息
    CourseWebVo getBaseCourseInfo(String courseId);

    // 获取前台讲师的所讲课程
    List<EduCourse> getTeacherFrontInfoList(String teacherId);
}
