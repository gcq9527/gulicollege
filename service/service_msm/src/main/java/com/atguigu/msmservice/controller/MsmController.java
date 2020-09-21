package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author gcq
 * @Create 2020-09-16
 */
@Api(description = "短信发送")
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("短信发送")
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        // 首先判断redis中是否有该验证码 防止用户重复发送
        String code = (String) redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        // redis中获取不到 发送短信 存入redis
        // 生成随机数值，传递阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        // 调用service发送短信
        boolean isSend = msmService.send(map,phone);
        if (isSend) {
            // 超时5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }


}