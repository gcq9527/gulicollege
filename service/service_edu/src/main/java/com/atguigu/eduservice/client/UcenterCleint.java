package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author gcq
 * @Create 2020-09-18
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterCleint {
    @GetMapping("/educenter/member/getMemberById/{id}")
    public R getMemberById(@PathVariable String id);
}