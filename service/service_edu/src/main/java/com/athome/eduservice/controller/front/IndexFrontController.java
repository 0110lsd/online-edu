package com.athome.eduservice.controller.front;

import com.athome.commonutils.R;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.EduTeacher;
import com.athome.eduservice.service.EduCourseService;
import com.athome.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/course/front")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("getHotSpot")
    public R getHotSpot() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courses = courseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 2");
        List<EduTeacher> teachers = teacherService.list(teacherWrapper);
        return R.ok().data("hotCourses", courses).data("hotTeachers", teachers);
    }
}
