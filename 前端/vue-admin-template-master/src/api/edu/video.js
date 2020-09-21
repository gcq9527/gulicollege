import request from '@/utils/request'

export default {
  // 根据课程id获取章节和小节内容
  addVideo(video) {
    return request({
        url: '/eduservice/video/addVideo',
        method: 'post',
        data: video
      })
  },
  deleteVideo(id) {
    return request({
        url: '/eduservice/video/' + id,
        method: 'delete'
      })
  },
  updateVideo(video) {
    return request({
        url: '/eduservice/video/updateVideo/',
        method: 'post',
        data: video
      })
  },
  getVideo(videoId) {
    return request({
        url: '/eduservice/video/getVideo/' + videoId,
        method: 'get'
      })
  },
  // 删除视频
  delteAliyunVod(id) {
    return request({
        url: '/eduvod/video/removeAlyVideo/' + id,
        method: 'delete'
      })
  }


 
 
}
