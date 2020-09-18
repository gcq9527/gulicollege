package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.UcenterCleint;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.vo.CommentVo;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
@Api("课程描述")
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    EduCommentService commentService;

    @ApiOperation("评论分页")
    @GetMapping("/PageComment/{page}/{limit}")
    public R PageComment(@PathVariable long page, @PathVariable long limit) {
        Page<EduComment> pageComment = new Page<>(page,limit);
        Map<String,Object> map = commentService.pageComment(pageComment);
        return R.ok().data(map);
    }


    // 2、添加评论
    // 2。1 拿到token查询用户内容插入数据库
    @PostMapping("/saveComment")
    public R saveComment(@RequestBody CommentVo commentVo) {
        commentService.saveComment(commentVo);
        return R.ok();
    }

}

