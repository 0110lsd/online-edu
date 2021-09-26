package com.athome.educenter.mapper;

import com.athome.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countUserByDate(String date);
}
