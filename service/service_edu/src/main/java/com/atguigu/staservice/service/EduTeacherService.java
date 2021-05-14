package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-17
 */
public interface EduTeacherService extends IService<EduTeacher> {
    // 查询前4名名师
    List<EduTeacher> selectTeacher();
    // 分页查询前台讲师的方法
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);


}
