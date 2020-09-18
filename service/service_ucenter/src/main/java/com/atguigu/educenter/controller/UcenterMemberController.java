package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-16
 */
@Api(description = "用户注册以及登录")
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {


    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember  member) {
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }



    // 注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类id,根据request对象获取头信息，返回用户id
        String meberId = JwtUtils.getMemberIdByJwtToken(request);

        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(meberId);
        return R.ok().data("userInfo",member);
    }

    @ApiOperation("根据id查询用户信息")
    @GetMapping("/getMemberById/{id}")
    public R getMemberById(@PathVariable String id) {
        UcenterMember ucenter = memberService.getById(id);
        return R.ok().data("ucenter",ucenter);
    }

    @GetMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

}

