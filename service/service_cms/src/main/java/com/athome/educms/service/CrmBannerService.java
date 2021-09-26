package com.athome.educms.service;

import com.athome.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-26
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> queryAll();
}
