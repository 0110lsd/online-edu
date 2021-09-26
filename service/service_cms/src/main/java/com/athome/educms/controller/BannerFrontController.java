package com.athome.educms.controller;

import com.athome.commonutils.R;
import com.athome.educms.entity.CrmBanner;
import com.athome.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/front/banner")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/queryAllBanners")
    public R queryAllBanners() {
        List<CrmBanner> bannerList = bannerService.queryAll();
        return R.ok().data("bannerList", bannerList);
    }
}
