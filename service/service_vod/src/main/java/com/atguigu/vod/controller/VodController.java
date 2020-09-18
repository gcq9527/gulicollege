package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.vod.Utils.ConstantVodUtils;
import com.atguigu.vod.Utils.InitObject;
import com.atguigu.vod.service.VodService;
import com.sun.org.apache.xpath.internal.operations.Mult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gcq
 * @Create 2020-09-15
 */
@Api("阿里云视频播放")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传视频至阿里云")
    @PostMapping("/uploadAliyVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeMoreAlyVideo(videoList);
        return R.ok();
    }

    @ApiOperation("根据视频id获取视频凭证")
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
         try {
            // 创建初始化对象
             DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
             // 创建获取凭证request 和response对象
             GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
             // request设置视频id
             request.setVideoId(id);
             // 调用方法得到凭证
             GetVideoPlayAuthResponse response = client.getAcsResponse(request);
             String playAuth = response.getPlayAuth();
             return R.ok().data("playAuth",playAuth);
         } catch (Exception e) {
             e.printStackTrace();
             throw new GuliException(20001,"获取凭证失败");
         }
    }

}