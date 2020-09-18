package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    EduCourseService courseService;
    @Autowired
    private VodClient vodClient;

    /**
     * 添加课程基本信息的方法
     *
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //1、向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "插入数据出错");
        }
        String cid = eduCourse.getId();


        //2、向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1、查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo vo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, vo);
        //2、查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        vo.setDescription(courseDescription.getDescription());
        return vo;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        //2、修改描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());

        eduCourseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public Map<String, Object> pageCourse(long current, long limit, CourseQuery courseQuery) {
        // 分页
        Page<EduCourse> pageCourse = new Page<>(current, limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //多条件组合查询
        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            wrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())) {
            wrapper.eq("status", courseQuery.getStatus());
        }
        //根据实际排序
        wrapper.orderByDesc("gmt_create");

        courseService.page(pageCourse, wrapper);

        // 总记录以及 数据
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return map;
    }

    @Override
    public void removeCourse(String courseId) {

        //1、根据课程id删除小节 eud_video
        eduVideoService.removeVideoByCourseId(courseId);

        //2、根据课程id删除章节 edu_chapter
        eduChapterService.removeChapterByCourseId(courseId);

        //3、根据课程id删除描述 edu_description
        eduCourseDescriptionService.removeById(courseId);

        //4、根据课程id删除课程本身 edu_course
        int i = baseMapper.deleteById(courseId);
        // 删除id错误
        if (i == 0) {
            throw new GuliException(20001, "删除课程id失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageTeacher, CourseFrontVo courseFrontVo) {
        // 根据讲师查询所属课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 判断条件值是否为空，不为空拼接
        // 一级分类
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }


        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduCourse> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext(); // 是否有下一页
        boolean previous = pageTeacher.hasPrevious(); // 是否存在上一页

        // 分页数据获取出来，放到map集合
        Map<String,Object> map = new HashMap<>();
        map.put("items",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("previous",previous);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
       return baseMapper.getBaseCourseInfo(courseId);
    }
}

