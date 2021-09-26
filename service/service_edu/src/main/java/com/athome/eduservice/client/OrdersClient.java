package com.athome.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-order")
public interface OrdersClient {

    @GetMapping("queryCoursePaid/{courseId}/{userId}")
    boolean queryCoursePaid(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);

}
