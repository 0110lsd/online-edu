package com.athome.servicesta.service;

import com.athome.servicesta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-06
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void statisticsByDate(String date);

    Map<String, Object> getGraphData(String type, String begin, String end);
}
