package com.athome.eduservice.service;

import com.athome.eduservice.entity.EduSubject;
import com.athome.eduservice.entity.subject.LevelOneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-15
 */
public interface EduSubjectService extends IService<EduSubject> {

    void readSubjectData(MultipartFile file, EduSubjectService subjectService);

    List<LevelOneSubject> getLevelOneSubjects();
}
