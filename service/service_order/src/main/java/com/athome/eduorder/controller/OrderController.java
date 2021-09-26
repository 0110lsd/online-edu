package com.athome.eduorder.controller;


import com.athome.commonutils.JwtUtils;
import com.athome.commonutils.R;
import com.athome.commonutils.order.OrderCourseInfo;
import com.athome.commonutils.order.OrderUserInfo;
import com.athome.eduorder.client.EduClient;
import com.athome.eduorder.client.UserClient;
import com.athome.eduorder.entity.Order;
import com.athome.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-07-03
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.saveOrder(courseId, userId);
        return R.ok().data("orderNo", orderNo);
    }

    /**
     * 根据订单号查询订单信息
     * @param orderNo
     * @return
     */
    @GetMapping("queryOrderByNo/{orderNo}")
    public R queryOrderByNo(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    /**
     * 根据课程id和用户id查询课程是否付款
     * @param courseId
     * @param userId
     * @return
     */
    @GetMapping("queryCoursePaid/{courseId}/{userId}")
    public boolean queryCoursePaid(@PathVariable String courseId, @PathVariable String userId) {
        QueryWrapper<Order> orderWrapper = new QueryWrapper<>();
        orderWrapper.eq("course_id", courseId);
        orderWrapper.eq("member_id", userId);
        orderWrapper.eq("status", 1);
        int count = orderService.count(orderWrapper);
        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }
}

