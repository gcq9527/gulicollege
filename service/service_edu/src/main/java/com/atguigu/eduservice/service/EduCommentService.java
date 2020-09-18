package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.entity.vo.CommentVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 分页查询评论
     * @param pageComment
     * @return
     */
    Map<String, Object> pageComment(Page<EduComment> pageComment);

    void saveComment(CommentVo commentVo);

}
