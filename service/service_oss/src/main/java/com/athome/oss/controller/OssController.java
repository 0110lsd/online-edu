package com.athome.oss.controller;

import com.athome.commonutils.R;
import com.athome.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/file")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("upload")
    public R uploadFile(MultipartFile file) {
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }
}
