package com.athome.eduservice.mapper;

import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.frontvo.CourseDetails;
import com.athome.eduservice.entity.vo.PublishCourseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseVo getPublishCourseVo(String courseId);

    CourseDetails queryCourseDetailsById(String courseId);
}
