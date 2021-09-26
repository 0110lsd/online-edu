package com.athome.eduorder.client;

import com.athome.commonutils.order.OrderUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UserClient {

    @GetMapping("/educenter/member/getOrderUserInfo/{userId}")
    OrderUserInfo getOrderUserInfo(@PathVariable("userId") String userId);
}
