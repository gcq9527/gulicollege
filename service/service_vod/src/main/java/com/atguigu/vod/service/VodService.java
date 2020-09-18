package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gcq
 * @Create 2020-09-15
 */
public interface VodService {
    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadVideoAly(MultipartFile file);

    /**
     * 删除多个视频
     * @param videoList
     */
    void removeMoreAlyVideo(List videoList);

}