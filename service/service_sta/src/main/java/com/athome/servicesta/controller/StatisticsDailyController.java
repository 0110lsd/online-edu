package com.athome.servicesta.controller;


import com.athome.commonutils.R;
import com.athome.servicesta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-06
 */
@RestController
@RequestMapping("/servicesta/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    @PostMapping("statisticsByDate/{date}")
    public R statisticsByDate(@PathVariable String date) {
        dailyService.statisticsByDate(date);
        return R.ok();
    }

    @GetMapping("queryGraphData/{type}/{begin}/{end}")
    public R queryGraphData(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String,Object> map = dailyService.getGraphData(type, begin, end);
        return R.ok().data(map);
    }

}

