package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String,Object> pageTeacher(long current, long limit, TeacherQuery teacherQuery);

    /**
     * 分页查询讲师
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherFronList(Page<EduTeacher> pageTeacher);

}
