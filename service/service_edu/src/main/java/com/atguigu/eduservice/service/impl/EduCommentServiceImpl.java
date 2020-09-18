package com.atguigu.eduservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterCleint;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CommentVo;
import com.atguigu.eduservice.entity.vo.UcenterVo;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    @Autowired
    UcenterCleint ucenterCleint;


    @Override
    public Map<String, Object> pageComment(Page<EduComment> pageComment) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();

        // 按照时间排序
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(pageComment,wrapper);


        List<EduComment> records = pageComment.getRecords();
        long current = pageComment.getCurrent();
        long pages = pageComment.getPages();
        long size = pageComment.getSize();
        long total = pageComment.getTotal();
        boolean hasNext = pageComment.hasNext(); // 是否有下一页
        boolean previous = pageComment.hasPrevious(); // 是否存在上一页

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
    public void saveComment(CommentVo commentVo) {
        // 保存评论
        EduComment eduComment = new EduComment();
        BeanUtils.copyProperties(commentVo,eduComment);
        // 拿到用户token 远程查询
        String ucenterId = commentVo.getMemberId();

        R r = ucenterCleint.getMemberById(ucenterId);
        Map<String, Object> data = r.getData();

        Object ucenter = data.get("ucenter");
        String jsonObject = JSON.toJSONString(ucenter);

        UcenterVo ucenterVo=JSON.parseObject(jsonObject,UcenterVo.class);
//        // 拿到会员信息
        eduComment.setAvatar(ucenterVo.getAvatar());
        eduComment.setNickname(ucenterVo.getNickname());
        eduComment.setMemberId(ucenterVo.getId());

        this.save(eduComment);
    }
}
