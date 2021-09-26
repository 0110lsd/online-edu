package com.athome.eduservice.service.impl;

import com.athome.eduservice.client.VodClient;
import com.athome.eduservice.entity.EduVideo;
import com.athome.eduservice.mapper.EduVideoMapper;
import com.athome.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if(videoSourceId != null)
                videoIds.add(videoSourceId);
        }
        if(videoIds.size() != 0) {
            String videoIdsStr = StringUtil.join(videoIds.toArray(), ",");
            vodClient.removeVideoById(videoIdsStr);
        }
    }
}
