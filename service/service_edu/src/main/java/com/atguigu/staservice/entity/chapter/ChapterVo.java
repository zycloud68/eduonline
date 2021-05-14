package com.atguigu.staservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;
    // 每一个章节后,有很多小节 一对多关系
    private List<VideoVo> children = new ArrayList<>();
}
