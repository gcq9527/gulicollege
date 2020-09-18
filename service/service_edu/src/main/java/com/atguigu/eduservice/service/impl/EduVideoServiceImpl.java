package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程id删除小节
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        //先删除视频
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        // 只查询具体一列
        videoQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoQueryWrapper);

        List<String> videoIds = new ArrayList<>();

        for(int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        if (videoIds.size() > 0) {
            vodClient.deleteBatch(videoIds);
        }

        // 在删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
