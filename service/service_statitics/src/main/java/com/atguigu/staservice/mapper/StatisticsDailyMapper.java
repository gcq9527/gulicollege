package com.atguigu.staservice.mapper;

import com.atguigu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-19
 */
@Mapper
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {

}
