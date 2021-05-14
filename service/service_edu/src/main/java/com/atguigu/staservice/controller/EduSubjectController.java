package com.atguigu.staservice.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.subject.OneSubject;
import com.atguigu.staservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-03-30
 */

@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;
    // 添加课程分类
    // 获取上传的过来的文件,最后将文件读取出来
    @ApiOperation(value = "添加课程分类")
    @PostMapping("/addSubject")
    public ResultResponse addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return ResultResponse.ok();
    }
    /**
     * 课程分类列表模式(树形)
     */
    @ApiOperation(value = "课程分类列表模式")
    @GetMapping("/getAllSubject")
    public ResultResponse getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return  ResultResponse.ok().data("list",list);
    }
}

