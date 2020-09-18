package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
@RestController
@RequestMapping("/eduorder/pay")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation("准备好微信支付数据")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        System.out.println("**************************************返回二维码map集合:"+map);
        return R.ok().data(map);
    }

    @ApiOperation("订单号 查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        // 如果返回map里面不为空，通过map获取订单状态
        // 支付成功
        if (map.get("trade_state").equals("SUCCESS")) {
            // 添加记录到支付表，更新订单表状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");

    }


}

