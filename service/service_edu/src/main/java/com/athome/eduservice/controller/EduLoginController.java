package com.athome.eduservice.controller;

import com.athome.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin            // 项目前端通过9528端口访问8008端口
public class EduLoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "Quite a man").data("name", "Boring Jimmy").data("avatar", "https://img2.woyaogexing.com/2021/06/10/cdec93915b98418caea1cf5fd2ba86dd!400x400.png");
    }
}
