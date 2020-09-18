package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author gcq
 * @Create 2020-09-16
 */
public interface MsmService {
    /**
     * 发送短信
     * @param map
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> map, String phone);

}