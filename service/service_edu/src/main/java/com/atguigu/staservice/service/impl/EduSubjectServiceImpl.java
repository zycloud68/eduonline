package com.atguigu.staservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.staservice.entity.EduSubject;
import com.atguigu.staservice.entity.excel.SubjectData;
import com.atguigu.staservice.entity.subject.OneSubject;
import com.atguigu.staservice.entity.subject.TwoSubject;
import com.atguigu.staservice.listener.SubjectExcelListener;
import com.atguigu.staservice.mapper.EduSubjectMapper;
import com.atguigu.staservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file              上传的文件
     * @param eduSubjectService 调用mysql语句添加的方法
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            //调用EasyExcel方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1. 查询所有的一级分类 就是当parentId=0,否册不为0就是二级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        // 1.1 查询是否parentId=0
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        // 2. 查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        QueryWrapper<EduSubject> twoId = wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoId);
        // 创建list集合,用于存储最终封装的数据,这一步是先在封装一级二级之前进行
        List<OneSubject> finalSubjectList = new ArrayList<>();
        // 3. 封装一级分类
        // 3.1 查询出来的一级分类,进行list集合遍历,得到每一个一级分类的对象,获取每一个一级分类的值
        // 并且封装到finalSubjectList中
        for (int i = 0; i <oneSubjectList.size() ; i++) {
            // 得到的oneSubjectList每一个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            // 把得到的eduSubject里面的值,获取出来,放到OneSubject中去
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            // 将多个OneSubject放到finalSubjectList中去
            finalSubjectList.add(oneSubject);

            // 在一级分类的下面查询到所有的二级分类
            // 并且创建每一个list集合中封装一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                // 获取每一个二级分类
                EduSubject ts = twoSubjectList.get(j);
                // 判断二级分类的parent_id是否和一级分类的id是否一样
                if (ts.getParentId().equals(eduSubject.getId())){
                    // 把ts中的值复制到TwoSubject中去,最后放到twoFinalSubjectList中去
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(ts,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 然后将一级分类下面的二级分类统一放到一级分类中去
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
