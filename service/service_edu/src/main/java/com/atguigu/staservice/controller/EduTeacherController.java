package com.atguigu.staservice.controller;

import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.EduTeacher;
import com.atguigu.staservice.entity.vo.TeacherQuery;
import com.atguigu.staservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;
    // 查询讲师表中的所有数据

    /**
     * 查询数据库中的所有信息
     * @return list
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public ResultResponse findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);

        return ResultResponse.ok().data("items",list);
    }

    /**
     * 逻辑删除讲师方法
     * @return  false or true
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    // @PathVariable 前台发送的路径中输入的东西
    public ResultResponse removeTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return ResultResponse.ok();
        }else {
            return ResultResponse.error();
        }

    }

    /**
     * 讲师的分页查询
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation(value = "讲师的分页查询")
    @GetMapping("/pageTeacher/{current}/{limit}")
   public ResultResponse queryListTeacher(@PathVariable long current,@PathVariable long limit){
        // 创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        // 调用方法实现分页
        // 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(eduTeacherPage,null);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        return ResultResponse.ok().data("total",total).data("rows",records);
    }


    /**
     * 讲师的组合条件查询
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "讲师的组合条件查询")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public ResultResponse queryTeacherInfo(@PathVariable Long current, @PathVariable long limit,
                                           @RequestBody(required = false) TeacherQuery teacherQuery){
        // 1. 首先创建分页 查询
        Page<EduTeacher> page = new Page(current,limit);
        // 这里是组合连接查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String names = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 2. 判断条件值是否为空
        if(!StringUtils.isEmpty(names)){
            // 注意 是wrapper是必须是你表中里面的值,第二个值上面方法定义的
            wrapper.like("name",names);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        // 排序
        wrapper.orderByDesc("gmt_create");
        // 调用方法实现条件查询分页
        eduTeacherService.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return ResultResponse.ok().data("total",total).data("rows",records);
    }


    /**
     * 5. 讲师信息的增加
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "讲师信息的增加")
    @PostMapping("addTeacher")
    public ResultResponse addTeacher (@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return ResultResponse.ok();
        }else {
            return ResultResponse.error();
        }
    }

    /**
     * 讲师信息的修改
     * 第一步: 先查询出来id,再根据id来修改信息
     */
    @ApiOperation(value = "讲师信息的修改")
    @GetMapping("getTeacher/{id}")
    public ResultResponse updateTeacherInfo(@PathVariable  String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return ResultResponse.ok().data("teacher",eduTeacher);
    }
    @ApiOperation(value = "讲师信息的修改")
    @PostMapping("updateTeacher")
    public ResultResponse updateTeacherInfo(@RequestBody EduTeacher  eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if (update){
            return ResultResponse.ok();
        }else{
            return  ResultResponse.error();
        }
    }

}

