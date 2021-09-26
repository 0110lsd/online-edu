package com.athome.eduservice.controller;


import com.athome.commonutils.R;
import com.athome.eduservice.client.VodClient;
import com.athome.eduservice.entity.EduVideo;
import com.athome.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok();
    }

    @DeleteMapping("{videoId}")
    public R removeVideoById(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideoById(videoSourceId);
        }
        videoService.removeById(videoId);           // 后删小节信息
        return R.ok();
    }

    @GetMapping("queryVideoById/{videoId}")
    public R queryVideoById(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

    @PostMapping("updateVideo")
    public R updateVideoById(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }

}

