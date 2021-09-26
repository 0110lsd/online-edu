package com.athome.eduorder.controller;


import com.athome.commonutils.R;
import com.athome.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("createQrCode/{orderNo}")
    public R createQrCode(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @GetMapping("queryPayState/{orderNo}")
    public R queryPayState(@PathVariable String orderNo) {
        Map<String,String> payInfo = payLogService.checkPayState(orderNo);
        if(payInfo == null) {
            return R.error().message("支付出错了");
        }
        if(payInfo.get("trade_state").equals("SUCCESS")) {
            payLogService.updatePayState(payInfo);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

