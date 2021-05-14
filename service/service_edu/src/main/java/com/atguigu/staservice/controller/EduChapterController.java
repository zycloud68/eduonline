package com.atguigu.staservice.controller;


import com.atguigu.commonutils.ResultResponse;
import com.atguigu.staservice.entity.EduChapter;
import com.atguigu.staservice.entity.chapter.ChapterVo;
import com.atguigu.staservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程Id查询大纲
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程Id查询大纲")
    @GetMapping("/getChapterVideo/{courseId}")
    public ResultResponse getChapterVideo(@PathVariable String courseId){
        // 根据courseId来查询章节信息
         List<ChapterVo> list =  chapterService.getChapterVideoByCourseId(courseId);
        return ResultResponse.ok().data("allChapterVideo",list);
    }


    /**
     * 添加课程章节
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "添加课程章节")
    @PostMapping("addChapter")
    public ResultResponse addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return ResultResponse.ok();
    }

    /**
     * 根据章节id来课程章节
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "根据章节id来课程章节")
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultResponse getChapterInfo(@PathVariable String chapterId){
        // 根据章节id来查询章节
        EduChapter eduChapter = chapterService.getById(chapterId);
        return ResultResponse.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节列表
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "修改章节列表")
    @PostMapping("updateChapter")
    public ResultResponse updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return ResultResponse.ok();

    }

    /**
     * 根据章节Id删除章节
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "根据章节Id删除章节")
    @DeleteMapping("{chapterId}")
    public ResultResponse deleteChapter(@PathVariable String chapterId){
        // 根据章节id来删除章节
        boolean flag = chapterService.deleteChapter(chapterId);
        //如果flag为真,代表删除章节成功
        if (flag){
            return ResultResponse.ok();
        }else{
            return ResultResponse.error();
        }

    }

}

