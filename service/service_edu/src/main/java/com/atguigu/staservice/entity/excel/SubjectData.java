package com.atguigu.staservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    // 一级课程分类
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    // 二级课程分类
    private String twoSubjectName;
}
