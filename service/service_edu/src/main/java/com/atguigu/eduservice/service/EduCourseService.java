package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程内容
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询内容
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据id查询课程确认信心
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 分页条件查询
     * @param current 当前页
     * @param limit 分页数
     * @param courseQuery 查询条件
     * @return
     */
    Map<String, Object> pageCourse(long current, long limit, CourseQuery courseQuery);

    /**
     * 根据课程id 删除课程
     * @param courseId 课程id
     */
    void removeCourse(String courseId);

    /**
     * 分页查询课程
     * @param pageCourse
     * @param courseFrontVo
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

}
