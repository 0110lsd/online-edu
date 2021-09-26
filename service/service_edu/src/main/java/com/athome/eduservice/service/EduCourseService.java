package com.athome.eduservice.service;

import com.athome.commonutils.order.OrderCourseInfo;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.frontvo.CourseDetails;
import com.athome.eduservice.entity.frontvo.FrontCourseVo;
import com.athome.eduservice.entity.vo.CourseInfoVo;
import com.athome.eduservice.entity.vo.PublishCourseVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoVo(String courseId);

    void updateCourse(CourseInfoVo courseInfoVo);

    PublishCourseVo getPublishCourseById(String courseId);

    void removeCourse(String courseId);

    Map<String, Object> getFrontCourseList(Page<EduCourse> page, FrontCourseVo courseVo);

    CourseDetails queryCourseDetailsById(String courseId);

    OrderCourseInfo queryCourseInfoById(String courseId);
}
