package com.athome.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.athome.commonutils.R;
import com.athome.vod.service.VodService;
import com.athome.vod.utils.ConstantVodUtils;
import com.athome.vod.utils.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传课程视频到阿里云
     * @param file
     * @return
     */
    @PostMapping("uploadVideoByAli")
    public R uploadVideoByAli(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 根据视频id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("removeVideoById/{videoId}")
    public R removeVideoById(@PathVariable String videoId) {
        // 初始化对象
        DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        request.setVideoIds(videoId);
        try {
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException("删除视频失败...");
        }
    }

    /**
     * 批量删除视频
     * @param videoIdList 要删除的视频id列表
     * @return
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@PathVariable("videoIdList")List<String> videoIdList) {
        vodService.removeMoreVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据id获取要播放的视频地址
     * @param videoId
     * @return
     */
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(videoId);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            throw new RuntimeException("获取凭证失败");
        }
    }
}
