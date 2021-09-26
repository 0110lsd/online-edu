package com.athome.eduservice.client;

import com.athome.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // @O
public class VodFileDegradeFeignClient implements VodClient {

    @Override // 调用失败会执行
    public R removeVideoById(String videoId) {
        return R.error().message("根据视频id删除视频失败了");
    }

    @Override  // 调用失败会执行
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("根据视频id列表删除多个视频失败了");
    }
}
