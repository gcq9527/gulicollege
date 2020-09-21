package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-19
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 某一天注册的用户
     * @param day
     */
    void registerCount(String day);

    /**
     * 图标显示，返回两部分数据，日期json数组，数据json数组，
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getShowData(String type, String begin, String end);

}
