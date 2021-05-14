package com.atguigu.staservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.staservice.entity.EduSubject;
import com.atguigu.staservice.entity.excel.SubjectData;
import com.atguigu.staservice.service.EduSubjectService;
import com.atguigu.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    /**
     * SubjectExcelListener不能被spring容器管理,不能实现数据库的操作
     * @param subjectData
     * @param analysisContext
     */
    public EduSubjectService eduSubjectService;
    // 无参构造
    public SubjectExcelListener() { }

    // 有参构造方法
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        // 判断实体类的数据是否为空,为空则表示异常处理
        if (subjectData == null){
            throw new GuliException(20001,"文件数据不能为空");
        }
        // 首先我们判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService,subjectData.getOneSubjectName());
        // 一级分类里面没有数据,则表示为空,可以添加分类信息
        if (existOneSubject == null){
            existOneSubject = new EduSubject(); // 将新的数据赋值给为空的existOneSubject值
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName()); // 获取新的一级分类名称,并且将其设值
            eduSubjectService.save(existOneSubject);
        }

        // 首先我们应当获取一级分类的id值,因为我们是根据一级分类的名称的id来确定二级分类
        String pid = existOneSubject.getId();
        // 判断二级分类,并且判断二级分类是否重复或者为空
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); //设置二级分类的名称
            eduSubjectService.save(existTwoSubject);
        }
    }
      // 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String oneSubjectName) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName); //数据库中的title字段
        wrapper.eq("parent_id","0"); //数据库中的parent_id字段
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }
    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String oneSubjectName,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
