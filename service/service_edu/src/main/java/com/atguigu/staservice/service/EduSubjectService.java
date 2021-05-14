package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduSubject;
import com.atguigu.staservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-30
 */
public interface EduSubjectService extends IService<EduSubject> {


    /**
     * 添加课程分类
     * @param file 上传的文件
     * @param eduSubjectService  调用mysql语句添加的方法
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    /**
     * 课程分类列表树形
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();

}
