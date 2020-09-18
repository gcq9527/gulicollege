package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Override
    public Map<String, Object> pageTeacher(long current, long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);


        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name",teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level",teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_create",teacherQuery.getEnd());
        }

        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("row",records);
        return map;
    }

    @Override
    public Map<String, Object> getTeacherFronList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 分页数据封装到pageTeacher对象中
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
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
}
