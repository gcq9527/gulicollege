package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author gcq
 * @Create 2020-09-14
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;
    public SubjectExcelListener(){};

    /**
     * 不能让Spring接管 通过构造注入Service对象
     * @param subjectService
     */
    public SubjectExcelListener(EduSubjectService subjectService){
        this.subjectService = subjectService;
    };

    /**
     * 读取excel内容 一行一行读取
     * @param data 实体类对象
     * @param context
     */
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取两个值，第一个值一级分类，第二个值二级以分类
        // 判断一级分类
        EduSubject eduOneSubject = this.existOneSubject(subjectService, data.getOneSubjectName());
        if (eduOneSubject == null) {
            // 没有相同一级分类 添加
            eduOneSubject = new EduSubject();
            eduOneSubject.setParentId("0");
            // 一级分类名称
            eduOneSubject.setTitle(data.getOneSubjectName());
            subjectService.save(eduOneSubject);
        }

        String pid = eduOneSubject.getId();

        // 二级分类
        // 判断二级分类是否重复
        EduSubject eduTwoSubject = this.existTwoSubject(subjectService, data.getTwoSubjectName(), pid);
        if (eduTwoSubject == null ) {
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(pid);
            // 二级分类名称
            eduTwoSubject.setTitle(data.getTwoSubjectName());
            subjectService.save(eduTwoSubject);

        }
    }

    /**
     * 判断一级分类不能重复
     * @param subjectService 业务对象
     * @param name 标题名
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    /**
     * 判断二级分类不能重复
     * @param subjectService 业务对象
     * @param name 标题名字
     * @param pid 父id
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }

    /**
     * 文件执行 执行
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}