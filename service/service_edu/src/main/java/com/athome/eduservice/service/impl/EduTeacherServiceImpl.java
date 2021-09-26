package com.athome.eduservice.service.impl;

import com.athome.eduservice.entity.EduTeacher;
import com.athome.eduservice.mapper.EduTeacherMapper;
import com.athome.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author BoringJimmy
 * @since 2021-06-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> pageFrontTeacher(long pageNo, long pageSize) {
        Map<String,Object> pageItems = new HashMap<>();
        Page<EduTeacher> pageTeacher = new Page<>(pageNo, pageSize);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页

        pageItems.put("items", records);
        pageItems.put("current", current);
        pageItems.put("pages", pages);
        pageItems.put("size", size);
        pageItems.put("total", total);
        pageItems.put("hasNext", hasNext);
        pageItems.put("hasPrevious", hasPrevious);

        return pageItems;
    }
}
