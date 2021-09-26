package com.athome.eduservice.controller.front;

import com.athome.commonutils.R;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.EduTeacher;
import com.athome.eduservice.service.EduCourseService;
import com.athome.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/eduservice/front/teacher")
@RestController
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @PostMapping("queryTeacherByPage/{pageNo}/{pageSize}")
    public R queryTeacherByPage(@PathVariable long pageNo, @PathVariable long pageSize) {
        Map<String,Object> pageItems = teacherService.pageFrontTeacher(pageNo, pageSize);
        return R.ok().data("pageItems", pageItems);
    }

    @GetMapping("/queryTeacherAndCourseById/{teacherId}")
    public R queryTeacherAndCourseById(@PathVariable String teacherId) {
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("teacher_id", teacherId);
        List<EduCourse> courses = courseService.list(courseWrapper);
        return R.ok().data("teacher", eduTeacher).data("courses", courses);
    }
}
