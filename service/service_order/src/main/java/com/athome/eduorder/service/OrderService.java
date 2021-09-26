package com.athome.eduorder.service;

import com.athome.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String userId);
}
