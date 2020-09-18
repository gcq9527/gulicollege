package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 获取课程大纲
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节
     * @param chapterId
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除章节
     * @param courseId 课程id
     */
    void removeChapterByCourseId(String courseId);

}
