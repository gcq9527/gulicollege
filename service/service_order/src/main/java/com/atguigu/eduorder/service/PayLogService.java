package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-18
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    /**
     * 根据订单号查询订单支付状态·
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新支付状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);

}
