package com.athome.eduservice.controller.front;

import com.athome.commonutils.JwtUtils;
import com.athome.commonutils.R;
import com.athome.eduservice.client.OrdersClient;
import com.athome.eduservice.entity.EduCourse;
import com.athome.eduservice.entity.chapter.ChapterVo;
import com.athome.eduservice.entity.frontvo.CourseDetails;
import com.athome.eduservice.entity.frontvo.FrontCourseVo;
import com.athome.eduservice.service.EduChapterService;
import com.athome.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front/course")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    @PostMapping("queryFrontCourseList/{pageNo}/{pageSize}")
    public R queryFrontCourseList(@PathVariable long pageNo, @PathVariable long pageSize, @RequestBody(required = false) FrontCourseVo courseVo) {
        Page<EduCourse> page = new Page<>(pageNo, pageSize);
        Map<String,Object> items = courseService.getFrontCourseList(page, courseVo);
        return R.ok().data("items", items);
    }

    @GetMapping("queryFrontCourseDetails/{courseId}")
    public R queryFrontCourseDetails(@PathVariable String courseId, HttpServletRequest request) {
        List<ChapterVo> chaptersInfo = chapterService.getChaptersByCourseId(courseId);
        CourseDetails courseDetails = courseService.queryCourseDetailsById(courseId);
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        boolean paid = ordersClient.queryCoursePaid(courseId, userId);
        return R.ok().data("chaptersInfo", chaptersInfo).data("courseDetails", courseDetails).data("paid", paid);
    }
}
