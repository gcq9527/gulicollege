package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gcq
 * @Create 2020-09-15
 */
@Data
public class CourseQuery {


    @ApiModelProperty(value = "课程标题")
    private String title;


    @ApiModelProperty(value = "课程状态，是否发布")
    private String status;
}