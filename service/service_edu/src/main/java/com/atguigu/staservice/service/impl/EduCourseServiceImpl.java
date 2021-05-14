package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.EduCourse;
import com.atguigu.staservice.entity.EduCourseDescription;
import com.atguigu.staservice.entity.frontVo.CourseFrontVo;
import com.atguigu.staservice.entity.frontVo.CourseWebVo;
import com.atguigu.staservice.entity.vo.CourseInfoVo;
import com.atguigu.staservice.entity.vo.CoursePublishVo;
import com.atguigu.staservice.mapper.EduCourseMapper;
import com.atguigu.staservice.service.EduChapterService;
import com.atguigu.staservice.service.EduCourseDescriptionService;
import com.atguigu.staservice.service.EduCourseService;
import com.atguigu.staservice.service.EduVideoService;
import com.atguigu.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService courseDescriptionService;
    @Autowired
    EduVideoService videoService;
    @Autowired
    EduChapterService eduChapterService;

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表添加课程基本的信息
        // 本来是将CourseInfoVo对象转换为eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        // 我们这边进行逻辑判断,如果insert返回值为0,则表明添加失败
        if (insert == 0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        // 在添加之后,我们应该获取课程id,因为课程描述信息应该跟课程id保持一致
        String cid = eduCourse.getId();
        // 添加描述信息是使用eduCourseDescription表,获取实例
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        // 将课程描述信息的id与课程添加信息的id保持一致
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    // 根据课程id来查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 根据课程id来查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        // 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update==0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        // 修改描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }
    // // 根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        // 1. 根据课程id删除小节信息
        videoService.removeCourseById(courseId);
        // 2. 根据课程id删除描述信息
        courseDescriptionService.removeCourseById(courseId);
        // 3. 根据课程id删除章节信息
        eduChapterService.removeCourseById(courseId);
        // 4. 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result==0){
            throw new GuliException(20001,"删除课程失败,请重新尝试");
        }
    }
    // 查询前8条热门课程,并按照降序排列
    @Override
    public List<EduCourse> selectCourses() {
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> eduCourseList = baseMapper.selectList(wrapperCourse);
        return eduCourseList;
    }
    // 条件查询带分页的查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper< EduCourse>  wrapper = new QueryWrapper<>();
        // 判断条件是否为空,不为空则拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){ // 一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){ // 二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){ //  关注度
            wrapper.orderByDesc("buy_count"); // 根据从高到底查询
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { // 最新时间
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){ //价格排序
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);
        // 将pageCourse里面的内容获取出来,保存到map集合中
        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long total = pageCourse.getTotal();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        boolean hasNext = pageCourse.hasNext(); // 下一页
        boolean hasPrevious = pageCourse.hasPrevious(); // 上一页
        // 将分页数据放到map集合中去
        Map<String,Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
    // 根据讲师id来查询所讲的课程信息
    @Override
    public List<EduCourse> getTeacherFrontInfoList(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

}
