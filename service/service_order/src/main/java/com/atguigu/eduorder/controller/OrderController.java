package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单信息，返回订单号")
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {

        // 创建订单号 返回订单号
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("/getOrderInfo/{orderId}")
    public R getCOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    @ApiOperation("根据课程id查询订单表中订单状态")
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        // 支付状态 1代表已经支付
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count > 0) { // 已支付
            return true;
        } else {
            return false;
        }
    }
}

