package com.athome.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.athome.eduservice.entity.EduSubject;
import com.athome.eduservice.entity.excel.SubjectData;
import com.athome.eduservice.entity.subject.LevelOneSubject;
import com.athome.eduservice.entity.subject.LevelTwoSubject;
import com.athome.eduservice.listener.ExcelSubjectListener;
import com.athome.eduservice.mapper.EduSubjectMapper;
import com.athome.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-15
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void readSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new ExcelSubjectListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LevelOneSubject> getLevelOneSubjects() {
        List<LevelOneSubject> res = new ArrayList<>();
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        for(EduSubject one : oneSubjects) {
            LevelOneSubject levelOne = new LevelOneSubject();
            BeanUtils.copyProperties(one, levelOne);
            res.add(levelOne);
            List<LevelTwoSubject> levelTwos = new ArrayList<>();
            for(EduSubject two : twoSubjects) {
                LevelTwoSubject levelTwo = new LevelTwoSubject();
                if(two.getParentId().equals(levelOne.getId())) {
                    BeanUtils.copyProperties(two, levelTwo);
                    levelTwos.add(levelTwo);
                }
            }
            levelOne.setChildren(levelTwos);
        }

        return res;
    }
}
