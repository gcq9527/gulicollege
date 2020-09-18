package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除video(小节内容)
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);

}
