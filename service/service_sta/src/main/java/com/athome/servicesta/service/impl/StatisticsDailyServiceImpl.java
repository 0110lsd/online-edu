package com.athome.servicesta.service.impl;

import com.athome.commonutils.R;
import com.athome.servicesta.client.UcenterClient;
import com.athome.servicesta.entity.StatisticsDaily;
import com.athome.servicesta.mapper.StatisticsDailyMapper;
import com.athome.servicesta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-06
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void statisticsByDate(String date) {

        QueryWrapper<StatisticsDaily> dailyWrapper = new QueryWrapper<>();  // 删除之前的数据
        dailyWrapper.eq("date_calculated", date);
        baseMapper.delete(dailyWrapper);

        R r = ucenterClient.countUserByDate(date);
        Integer registerUser = (Integer)r.getData().get("count");
        int loginNum = RandomUtils.nextInt(100, 200);
        int videoViewNum = RandomUtils.nextInt(100, 200);
        int courseNum = RandomUtils.nextInt(100, 200);
        StatisticsDaily daily = new StatisticsDaily();
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setRegisterNum(registerUser);
        daily.setDateCalculated(date);
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getGraphData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> staWrapper = new QueryWrapper<>();
        staWrapper.between("date_calculated", begin, end);
        staWrapper.select("date_calculated", type);
        List<StatisticsDaily> dailies = baseMapper.selectList(staWrapper);
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> typeCountList = new ArrayList<>();
        for(StatisticsDaily daily : dailies) {
            switch (type) {
                case "login_num":
                    typeCountList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    typeCountList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    typeCountList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    typeCountList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
            dateCalculatedList.add(daily.getDateCalculated());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("typeCountList", typeCountList);
        map.put("dateCalculatedList", dateCalculatedList);
        return map;
    }
}
