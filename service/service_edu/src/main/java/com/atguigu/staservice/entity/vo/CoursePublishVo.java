package com.atguigu.staservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne; // 一级分类
    private String subjectLevelTwo; // 二级分类
    private String teacherName;
    private String price;//只用于显示
}
