package com.athome.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublishCourseVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
