import request from '@/utils/request'

export default {
  // 根据课程id获取章节和小节内容
  getAllChapterVideo(courseId) {
    return request({
        url: '/eduservice/chapter/getChapterVideo/'+courseId,
        method: 'get'
      })
  },
  addChapter(chapter) {
    return request({
      url: `/eduservice/chapter/addChapter`,
      method: 'post',
      data: chapter
    })
  },
  getChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/getChapterInfo/` + chapterId,
      method: 'get'
    })
  },
  // 修改章节
  updateChapter(chapter) {
    return request({
      url: `/eduservice/chapter/updateChapter`,
      method: 'post',
      data: chapter
    })
  },
  deleteChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/` + chapterId,
      method: 'delete'
    })
  }
 
}
