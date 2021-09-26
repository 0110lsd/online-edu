package com.athome.eduorder.client;

import com.athome.commonutils.order.OrderCourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {

    @GetMapping("/eduservice/course/getOrderCourseInfo/{courseId}")
    OrderCourseInfo getOrderCourseInfo(@PathVariable String courseId);
}
