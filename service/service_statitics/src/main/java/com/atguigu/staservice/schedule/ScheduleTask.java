package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @author gcq
 * @Create 2020-09-19
 */
@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService staService;


  /*  @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("**********执行了");
    }*/

    // 每天凌晨一点，把前一天数据进行查询添加
    @Scheduled(cron ="0 0 1 * * ?")
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}