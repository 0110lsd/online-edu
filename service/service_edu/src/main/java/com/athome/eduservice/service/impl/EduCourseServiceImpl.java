package com.athome.eduservice.service.impl;

import com.athome.commonutils.order.OrderCourseInfo;
import com.athome.eduservice.entity.EduChapter;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.EduCourseDescription;
import com.athome.eduservice.entity.frontvo.CourseDetails;
import com.athome.eduservice.entity.frontvo.FrontCourseVo;
import com.athome.eduservice.entity.vo.CourseInfoVo;
import com.athome.eduservice.entity.vo.PublishCourseVo;
import com.athome.eduservice.mapper.EduCourseMapper;
import com.athome.eduservice.service.EduChapterService;
import com.athome.eduservice.service.EduCourseDescriptionService;
import com.athome.eduservice.service.EduCourseService;
import com.athome.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0) {
            throw new RuntimeException("添加课程信息失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoVo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new RuntimeException("更新课程信息失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public PublishCourseVo getPublishCourseById(String courseId) {
        PublishCourseVo publishCourseVo = baseMapper.getPublishCourseVo(courseId);
        return publishCourseVo;
    }

    @Override
    public void removeCourse(String courseId) {
        courseDescriptionService.removeById(courseId);  // 删除课程描述
        chapterService.removeByCourseId(courseId);      // 删除章节
        videoService.removeByCourseId(courseId);        // 删除小结
        int del = baseMapper.deleteById(courseId);      // 删除课程
        if(del == 0) {
            throw new RuntimeException("删除课程信息失败");
        }
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> page, FrontCourseVo courseVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseVo.getSubjectId())) {
            wrapper.eq("subject_id", courseVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count", courseVo.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(courseVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create", courseVo.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(courseVo.getPriceSort())) {
            wrapper.orderByDesc("price", courseVo.getPriceSort());
        }
        baseMapper.selectPage(page, wrapper);

        List<EduCourse> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();//下一页
        boolean hasPrevious = page.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseDetails queryCourseDetailsById(String courseId) {
        CourseDetails courseDetails = baseMapper.queryCourseDetailsById(courseId);
        return courseDetails;
    }

    @Override
    public OrderCourseInfo queryCourseInfoById(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        OrderCourseInfo courseInfo = new OrderCourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);
        return courseInfo;
    }
}
