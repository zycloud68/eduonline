package com.atguigu.staservice.controller.front;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.chapter.ChapterVo;
import com.atguigu.staservice.entity.frontVo.CourseFrontVo;
import com.atguigu.staservice.entity.frontVo.CourseWebVo;
import com.atguigu.staservice.service.EduChapterService;
import com.atguigu.staservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService chapterService;

    /**
     * 条件查询带分页的查询课程
     * @param page 当前页数
     * @param limit 每页显示的条目数
     * @param courseFrontVo 返回值
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public ResultResponse getFrontCourseList(@PathVariable long page,@PathVariable long limit,@RequestBody(required = false) CourseFrontVo courseFrontVo){
        // 条件查询带分页的查询课程
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        // 返回以map的方式返回
        Map<String,Object> map = eduCourseService.getCourseFrontList(pageCourse,courseFrontVo);
        return ResultResponse.ok().data(map);
    }

    /**
     *  课程详情的方法
     * @param courseId  课程id
     * @return
     */

    @GetMapping("getFrontCourseInfo/{courseId}")
    public ResultResponse getFrontCourseInfo(@PathVariable String courseId){
        // 根据课程获取详细信息
         CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
         // 根据课程id来查询章节和小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return ResultResponse.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    // 根据课程id查询课程信息
    @PostMapping("/getCourseInfoOrder/{id}")
        public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        // 将courseInfo里面的信息复制到courseWebVoOrder中去
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return  courseWebVoOrder;
    }

}
