package com.athome.educenter.controller;


import com.athome.commonutils.JwtUtils;
import com.athome.commonutils.R;
import com.athome.commonutils.order.OrderUserInfo;
import com.athome.educenter.entity.UcenterMember;
import com.athome.educenter.entity.vo.RegisterVo;
import com.athome.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-28
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 用户登录
     * @param member
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 用户注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember user = memberService.getById(memberId);
        return R.ok().data("user", user);
    }

    /**
     * 通过用户id获取用户订单信息
     * @param userId
     * @return
     */
    @GetMapping("getOrderUserInfo/{userId}")
    public OrderUserInfo getOrderUserInfo(@PathVariable String userId) {
        UcenterMember user = memberService.getById(userId);
        OrderUserInfo orderUser = new OrderUserInfo();
        BeanUtils.copyProperties(user, orderUser);
        return orderUser;
    }

    /**
     * 根据日期获取用户数目
     * @param date
     * @return
     */
    @GetMapping("countUserByDate/{date}")
    public R countUserByDate(@PathVariable String date) {
        Integer count = memberService.queryUserByDate(date);
        return R.ok().data("count", count);
    }

}

