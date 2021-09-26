package com.athome.eduservice.controller;


import com.athome.commonutils.R;
import com.athome.eduservice.entity.EduSubject;
import com.athome.eduservice.entity.subject.LevelOneSubject;
import com.athome.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-15
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.readSubjectData(file, subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubjects")
    public R getAllSubjects() {
        List<LevelOneSubject> items = subjectService.getLevelOneSubjects();
        return R.ok().data("items", items);
    }
}

