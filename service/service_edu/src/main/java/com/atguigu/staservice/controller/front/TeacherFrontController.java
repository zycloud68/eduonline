package com.atguigu.staservice.controller.front;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.EduTeacher;
import com.atguigu.staservice.service.EduCourseService;
import com.atguigu.staservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    // 分页查询前台讲师的方法
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public ResultResponse getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        // 返回分页详情
        return ResultResponse.ok().data(map);
    }
    // 讲师的详细信息
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public ResultResponse getTeacherFrontInfo(@PathVariable String teacherId){
        // 1. 根据课程id来查询讲师的基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 2. 根据讲师id来查询所讲的课程信息
        List<EduCourse> courseList = courseService.getTeacherFrontInfoList(teacherId);
        return ResultResponse.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }


}
