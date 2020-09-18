package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author gcq
 * @Create 2020-09-17
 */
@Api("讲师")
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontControoller {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService eduCourseService;


    @ApiOperation("分页查询讲师方法")
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFronList(pageTeacher);
        return R.ok().data(map);
    }

    @ApiOperation("讲师详情查询")
    @GetMapping("/getTeacherFronInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }


}