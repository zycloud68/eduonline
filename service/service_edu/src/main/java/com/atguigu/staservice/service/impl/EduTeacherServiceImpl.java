package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.EduTeacher;
import com.atguigu.staservice.mapper.EduTeacherMapper;
import com.atguigu.staservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-17
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public List<EduTeacher> selectTeacher() {
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(wrapperTeacher);
        return eduTeachers;
    }
    // 分页查询前台讲师的方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage) {
        // 首先获取老师基本信息数据
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 降序排列
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage,wrapper);
        List<EduTeacher> records = teacherPage.getRecords();
        long pages = teacherPage.getPages();
        long total = teacherPage.getTotal();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        boolean hasNext = teacherPage.hasNext(); // 下一页
        boolean hasPrevious = teacherPage.hasPrevious(); // 上一页
        // 将上面数据封装到map集合中返回
        Map<String,Object> map = new HashMap<>();
        map.put("pages",pages);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("items",records);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

}
