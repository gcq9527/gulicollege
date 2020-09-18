package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     后台管理操作
 * </p>
 *
 * @author testjava
 * @since 2020-09-16
 */
@Api("网站首页Banner列表")
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation("获取首页banner")
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @ApiOperation("添加banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation("根据id获取banner")
    @GetMapping("/get/{id}")
    public R getBanner(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item",banner);
    }

    @ApiOperation("修改banner")
    @PutMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation("删除banner")
    @DeleteMapping("/deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

