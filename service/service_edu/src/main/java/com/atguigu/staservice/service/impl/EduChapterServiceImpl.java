package com.atguigu.staservice.service.impl;

import com.atguigu.staservice.entity.EduChapter;
import com.atguigu.staservice.entity.EduVideo;
import com.atguigu.staservice.entity.chapter.ChapterVo;
import com.atguigu.staservice.entity.chapter.VideoVo;
import com.atguigu.staservice.mapper.EduChapterMapper;
import com.atguigu.staservice.service.EduChapterService;
import com.atguigu.staservice.service.EduVideoService;
import com.atguigu.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zycloud@163.com
 * @since 2021-04-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1. 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        // 判断course_id是否和前台传过来的courseId一样
        wrapperChapter.eq("course_id",courseId);
        // 查询所有的章节
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2. 根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        // 因为是另外一张表,根据注解在容器中可以引用  private EduVideoService videoService;
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 3. 遍历查询章节list集合,进行封装
        // 3.1 为了方便查询的章节容易存储,我们建立list集合
        List<ChapterVo> finalList = new ArrayList<>();
        // 3.2 遍历查询到的所有章节
        for (int i = 0; i < eduChapterList.size(); i++) {
            // 每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            // 我们是将查询的eduChapter封装到ChapterVo中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            // 最后将封装的chapterVo放到最终的finalList集合中去
            finalList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();
            // 4. 遍历查询小节list集合,进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                // 首先确保获取小节的id跟章节的id是一样的
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    // 我们将查询到的eduVideo封装到VideoVo中去
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    // 放到小节去封装集合
                    videoList.add(videoVo);
                }
                // 将封装好的小节集合,放到章节中去
                chapterVo.setChildren(videoList);
            }
        }
        return finalList;
    }

    /**
     * 根据章节Id删除章节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 我们这里考虑当章节为空的时候,可以删除,当章节里面还有小节的时候,我们不能删除成功
        // 1. 根据chapterId来查询章节是否为空,实际上就是看是否有小节内容
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw  new GuliException(20001,"章节未能删除");
        }else{
            // 删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
    // 根据课程id删除章节信息
    @Override
    public void removeCourseById(String courseId) {
        QueryWrapper<EduChapter> wrapper  = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
