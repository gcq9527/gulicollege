package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author gcq
 * @Create 2020-09-13
 */
@Api(description = "用户登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {


    @ApiOperation("登录")
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}