package com.athome.msmservice.controller;

import com.aliyuncs.utils.StringUtils;
import com.athome.commonutils.R;
import com.athome.msmservice.service.MsmService;
import com.athome.msmservice.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送短信验证信息
     * @param phoneNum
     * @return
     */
    @GetMapping("sendMsm/{phoneNum}")
    public R sendMsm(@PathVariable String phoneNum) {
        String code = redisTemplate.opsForValue().get(phoneNum);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        String fourBitRandom = RandomUtils.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", fourBitRandom);
        boolean isSend = msmService.send(param, phoneNum);
        if(isSend) {
            redisTemplate.opsForValue().set(phoneNum, fourBitRandom, 5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("发送验证码失败");
    }
}
