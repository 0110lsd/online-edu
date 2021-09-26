package com.athome.eduservice.client;

import com.athome.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) // 调用服务的名称
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeVideoById/{videoId}")       // 全路径
    R removeVideoById(@PathVariable("videoId") String videoId);     // PathVariable注解需要加参数名字

    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBatch(@PathVariable("videoIdList") List<String> videoIdList);
}
