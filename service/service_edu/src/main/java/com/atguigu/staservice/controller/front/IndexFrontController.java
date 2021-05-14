package com.atguigu.staservice.controller.front;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.EduTeacher;
import com.atguigu.staservice.service.EduCourseService;
import com.atguigu.staservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;
    /**
     * 查询前8条热门课程
     * @return
     */
    @GetMapping("index")
    public ResultResponse index(){
        // 查询前8条热门课程
        List<EduCourse> eduList =  courseService.selectCourses();
        List<EduTeacher> teacherList =  teacherService.selectTeacher();
        return ResultResponse.ok().data("eduList",eduList).data("teacherList",teacherList);
    }


}
