package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Api(description = "课程")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 完成分页 TODO
     * @return
     */
    @ApiOperation("查询课程列表")
    @GetMapping("/getCourseList")
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }


    @ApiOperation("分页查询")
    @PostMapping("/pageCourseList/{current}/{limit}")
    public R pageCourseList(@ApiParam(name ="当前页码") @PathVariable long current,
                            @PathVariable long limit,
                            @RequestBody(required = false) CourseQuery courseQuery) {
        Map<String,Object> map = courseService.pageCourse(current,limit,courseQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation("添加课程信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo vo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",vo);
    }

    @ApiOperation("更新课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }



    @ApiOperation("根据id查询课程确认信息")
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    @ApiOperation("根据id更新课程发布状态")
    @PostMapping("/publishCourse/{id}")
    public R publiisCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        // 设置课程发布状态
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation("根据课程Id删除课程")
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

