package com.athome.eduorder.service;

import com.athome.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    Map<String, String> checkPayState(String orderNo);

    void updatePayState(Map<String, String> payInfo);
}
