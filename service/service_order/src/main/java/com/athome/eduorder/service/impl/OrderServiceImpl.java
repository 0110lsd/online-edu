package com.athome.eduorder.service.impl;

import com.athome.commonutils.order.OrderCourseInfo;
import com.athome.commonutils.order.OrderUserInfo;
import com.athome.eduorder.client.EduClient;
import com.athome.eduorder.client.UserClient;
import com.athome.eduorder.entity.Order;
import com.athome.eduorder.mapper.OrderMapper;
import com.athome.eduorder.service.OrderService;
import com.athome.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UserClient userClient;

    @Override
    public String saveOrder(String courseId, String userId) {
        OrderCourseInfo orderCourseInfo = eduClient.getOrderCourseInfo(courseId);
        OrderUserInfo orderUserInfo = userClient.getOrderUserInfo(userId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(orderCourseInfo.getTitle());
        order.setCourseCover(orderCourseInfo.getCover());
        order.setTeacherName(orderCourseInfo.getTeacherName());
        order.setTotalFee(orderCourseInfo.getPrice());
        order.setMemberId(userId);
        order.setMobile(orderUserInfo.getMobile());
        order.setNickname(orderUserInfo.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();

    }
}
