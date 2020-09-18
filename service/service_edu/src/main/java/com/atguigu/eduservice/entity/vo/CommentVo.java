package com.atguigu.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @author gcq
 * @Create 2020-09-18
 */
@Data
public class CommentVo {

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "评论内容")
    private String content;



}