package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 课程分类列表(树形)
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //1、查询所有一级分类 --> 条件 parent_id = 0
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper();
        oneWrapper.eq("parent_id",0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

        //2、查询所有二级分类 --> 条件 parent_id ！=0
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper();
        twoWrapper.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        
        //3、封装一级分类
        List<OneSubject> findOneSubject = new ArrayList<>();
        //循环拿到每一个一级分类
        for(int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            OneSubject oneSubject = new OneSubject();
            //将eduSubject和oneSubject中共同属性 拷贝给 oneSubject
            BeanUtils.copyProperties(eduSubject,oneSubject);

            findOneSubject.add(oneSubject);

            //4、封装二级分类
            List<TwoSubject> findTwoSubject = new ArrayList<>();
            for(int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject tsubject = twoSubjectList.get(m);

                if (tsubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tsubject,twoSubject);
                    findTwoSubject.add(twoSubject);
                }
            }
            oneSubject.setChildren(findTwoSubject);
        }
        return findOneSubject;
    }
}
