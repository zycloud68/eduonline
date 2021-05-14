package com.atguigu.staservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.vo.CourseInfoVo;
import com.atguigu.staservice.entity.vo.CoursePublishVo;
import com.atguigu.staservice.entity.vo.CourseQuery;
import com.atguigu.staservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-01
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 获取课程所有信息
     * @return
     */
    @ApiOperation(value="获取所有课程信息")
    @GetMapping
    public ResultResponse getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return  ResultResponse.ok().data("list",list);
    }

    /**
     * 课程分页信息展示
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation(value="课程分页信息展示")
    @GetMapping("/pageCourse/{current}/{limit}")
    public ResultResponse queryListCourse(@PathVariable long current,@PathVariable long limit){
        Page<EduCourse> eduCoursePage = new Page<>(current,limit);
       courseService.page(eduCoursePage,null);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return ResultResponse.ok().data("total", total).data("rows",records);
    }

    // 课程信息组合条件查询
    @ApiOperation(value = "课程信息组合条件查询")
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public  ResultResponse pageCourseCondition(@PathVariable Long current,@PathVariable long limit,
     @RequestBody(required = false)  CourseQuery courseQuery){
        // 1. 创建分页信息
        Page<EduCourse> page = new Page<>(current,limit);
        // 进行组合查询
        QueryWrapper<EduCourse> wrapper  = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        Integer status = courseQuery.getStatus();
        // 2. 判断条件是否为空,当为空的时候,显示所有信息
        if (!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        wrapper.orderByDesc("status");
        courseService.page(page,wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return ResultResponse.ok().data("total", total).data("rows",records);
    }


    /**
     * 添加课程基本信息方法
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    @ApiOperation(value = "添加课程基本信息方法")
    public ResultResponse addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id,为后面的添加课程大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return ResultResponse.ok().data("courseId",id);
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation(value = "根据课程id查询课程基本信息")
    public ResultResponse getCourseInfo(@PathVariable String courseId){
        // 根据课程id来查询课程信息
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return ResultResponse.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public ResultResponse updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return  ResultResponse.ok();
    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    // 根据课程id查询课程确认信息
    @ApiOperation(value = "根据课程id查询课程确认信息")
   @GetMapping("getPublishCourseInfo/{id}")
    public ResultResponse getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo  coursePublishVo = courseService.publishCourseInfo(id);
        return ResultResponse.ok().data("publishCourse",coursePublishVo);
   }


    /**
     * 课程最终发布
     * @param id
     * @return
     */
  // 课程最终发布
    @ApiOperation(value = "课程最终发布")
    @PostMapping("publishCourse/{id}")
    public ResultResponse publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return  ResultResponse.ok();
    }

    @ApiOperation(value = "根据课程id删除课程信息")
    @DeleteMapping("{courseId}")
    public ResultResponse deleteCourse(@PathVariable String courseId){
    courseService.removeCourse(courseId);
    return ResultResponse.ok();
    }




}

