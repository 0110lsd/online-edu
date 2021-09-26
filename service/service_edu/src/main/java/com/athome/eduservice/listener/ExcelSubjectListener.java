package com.athome.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.athome.eduservice.entity.EduSubject;
import com.athome.eduservice.entity.excel.SubjectData;
import com.athome.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class ExcelSubjectListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public ExcelSubjectListener() {}

    public ExcelSubjectListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new RuntimeException("空数据");
        }
        EduSubject eduSubject = existOneSubjectData(subjectService, subjectData.getOneSubjectName());
        if(eduSubject == null) {
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduSubject);
        }
        String pid = eduSubject.getId();
        EduSubject eduTwoSubject = existTwoSubjectData(subjectService, subjectData.getTwoSubjectName(), pid);
        if(eduTwoSubject == null) {
            eduTwoSubject = new EduSubject();
            eduTwoSubject.setParentId(pid);
            eduTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduTwoSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    // 判断一级分类有无重复
    private EduSubject existOneSubjectData(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> qw = new QueryWrapper<>();
        qw.eq("title", name);
        qw.eq("parent_id", 0);
        EduSubject oneSubject = subjectService.getOne(qw);
        return oneSubject;
    }

    // 判断二级分类有无重复
    private EduSubject existTwoSubjectData(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> qw = new QueryWrapper<>();
        qw.eq("title", name);
        qw.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(qw);
        return twoSubject;
    }
}
