package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exception.BizCodeEnume;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin //解决跨越
public class EduTeacherController {


    @Autowired
    private EduTeacherService eduTeacherService;

    //1、查询讲师表中所有数据
    //rest风格
    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation("删除讲师id")
    @DeleteMapping("/{id}")
    public R removeByID(@ApiParam(name="id",value = "教师id",required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        }else{
            return R.error();
        }
    }
    @ApiOperation("分页")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit) {

        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        eduTeacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("分页条件查询")
    @PostMapping("/PageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Map<String, Object> stringObjectMap = eduTeacherService.pageTeacher(current, limit, teacherQuery);


        return R.ok().data("total",stringObjectMap.get("total")).data("rows",stringObjectMap.get("row"));
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R selectById(@PathVariable String id) {
        EduTeacher byId = eduTeacherService.getById(id);

        return R.ok().data("teacher",byId);
    }

    @ApiOperation("根据id更新讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

