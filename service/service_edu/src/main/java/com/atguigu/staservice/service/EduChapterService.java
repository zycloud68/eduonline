package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.EduChapter;
import com.atguigu.staservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-01
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
    // 根据课程id删除章节信息
    void removeCourseById(String courseId);
}
