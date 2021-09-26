package com.athome.educms.controller;


import com.athome.commonutils.R;
import com.athome.educms.entity.CrmBanner;
import com.athome.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-26
 */
@RestController
@RequestMapping("/educms/admin/banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/pageBanner/{pageNo}/{pageSize}")
    public R pageBanner(@PathVariable long pageNo, @PathVariable long pageSize) {
        Page<CrmBanner> page = new Page<>(pageNo, pageSize);
        bannerService.page(page, null);
        return R.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @GetMapping("/queryBannerById/{id}")
    public R queryBannerById(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner", banner);
    }

    @PostMapping("/updateBannerInfo")
    public R updateBannerInfo(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("/removeBannerById/{id}")
    public R removeBannerById(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

