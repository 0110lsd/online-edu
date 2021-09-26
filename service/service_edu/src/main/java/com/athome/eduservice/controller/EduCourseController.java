package com.athome.eduservice.controller;


import com.athome.commonutils.R;
import com.athome.commonutils.order.OrderCourseInfo;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.EduTeacher;
import com.athome.eduservice.entity.vo.CourseInfoVo;
import com.athome.eduservice.entity.vo.CourseQueryWrapper;
import com.athome.eduservice.entity.vo.PublishCourseVo;
import com.athome.eduservice.service.EduCourseDescriptionService;
import com.athome.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 添加课程
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String cid = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", cid);
    }

    /**
     * 通过课程id查询课程信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfo = courseService.getCourseInfoVo(courseId);
        return R.ok().data("courseInfo", courseInfo);
    }

    /**
     * 更新课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    /**
     * 通过课程id查询发布课程信息
     * @param courseId
     * @return
     */
    @GetMapping("queryPublishCourseVo/{courseId}")
    public R queryPublishCourseVo(@PathVariable String courseId) {
        PublishCourseVo publishCourse = courseService.getPublishCourseById(courseId);
        return R.ok().data("publishCourse", publishCourse);
    }

    /**
     * 通过课程id发布课程
     * @param courseId
     * @return
     */
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 查询所有课程信息
     * @return
     */
    @GetMapping("queryAllCourses")
    public R queryAllCourses() {
        List<EduCourse> courses = courseService.list(null);
        return R.ok().data("courses", courses);
    }

    /**
     * 课程分页
     * @param pageNo
     * @param pageSize
     * @param courseWrapper
     * @return
     */
    @PostMapping("queryCoursePageByWrapper/{pageNo}/{pageSize}")
    public R queryCoursePageWrapper(@PathVariable long pageNo, @PathVariable long pageSize, @RequestBody(required = false)CourseQueryWrapper courseWrapper) {
        Page<EduCourse> page = new Page<>(pageNo, pageSize);
        QueryWrapper<EduCourse> qw = new QueryWrapper<>();
        String title = courseWrapper.getTitle();
        if(!StringUtils.isEmpty(title)) {
            qw.like("title", title);
        }
        String begin = courseWrapper.getBegin();
        if(!StringUtils.isEmpty(begin)) {
            qw.ge("gmt_create", begin);
        }

        String end = courseWrapper.getEnd();
        if(!StringUtils.isEmpty(end)) {
            qw.le("gmt_create", end);
        }
        courseService.page(page, qw);
        long pageTotal = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("coursePage", records).data("pageTotal", pageTotal);
    }

    /**
     * 通过课程id删除课程
     * @param courseId
     * @return
     */
    @DeleteMapping("{courseId}")
    public R removeCourseById(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

    /**
     * 通过课程id查询购买页面课程信息
     * @param courseId
     * @return
     */
    @GetMapping("getOrderCourseInfo/{courseId}")
    public OrderCourseInfo getOrderCourseInfo(@PathVariable String courseId) {
        OrderCourseInfo courseInfo = courseService.queryCourseInfoById(courseId);
        return courseInfo;
    }
}

