package com.atguigu.eduservice.controller;


import com.alibaba.excel.util.StringUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exception.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-14
 */
@Api("小节")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @ApiOperation("添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节 同时删除阿里云视频
     * @param id
     * @return
     */
    // TODO 这个方法完善 删除小结，同时删除阿里云视频
    @ApiOperation("删除小节")
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //没有阿里云视频id
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001,"删除视频更新失败，熔断器");
            }

        }
        //删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }


    @ApiOperation("更新小节")
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }
    @ApiOperation("根据id查询小节")
    @GetMapping("/getVideo/{id}")
    public R getVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("video",eduVideo);
    }



}

