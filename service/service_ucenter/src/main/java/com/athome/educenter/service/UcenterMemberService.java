package com.athome.educenter.service;

import com.athome.educenter.entity.UcenterMember;
import com.athome.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer queryUserByDate(String date);
}
