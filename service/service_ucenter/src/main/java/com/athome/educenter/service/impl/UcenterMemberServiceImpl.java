package com.athome.educenter.service.impl;

import com.athome.commonutils.JwtUtils;
import com.athome.commonutils.MD5;
import com.athome.educenter.entity.UcenterMember;
import com.athome.educenter.entity.vo.RegisterVo;
import com.athome.educenter.mapper.UcenterMemberMapper;
import com.athome.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if(ucenterMember == null) {
            throw new RuntimeException("登录失败");
        }
        String encrypt = MD5.encrypt(password);
        if(!encrypt.equals(ucenterMember.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        if(ucenterMember.getIsDisabled()) {
            throw new RuntimeException("登录失败");
        }
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
        StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("注册失败");
        }

        // String redisCode = redisTemplate.opsForValue().get(mobile);

        if(!"doge".equals(code)) {
            throw new RuntimeException("验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new RuntimeException("号码已注册");
        }
        UcenterMember um = new UcenterMember();
        um.setMobile(mobile);
        um.setNickname(nickname);
        um.setPassword(MD5.encrypt(password));
        um.setIsDisabled(false);
        um.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(um);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer queryUserByDate(String date) {
        Integer count = baseMapper.countUserByDate(date);
        return count;
    }
}
