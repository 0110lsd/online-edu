package com.athome.eduservice.controller;


import com.athome.commonutils.R;
import com.athome.eduservice.entity.EduTeacher;
import com.athome.eduservice.entity.vo.TeacherQueryWrapper;
import com.athome.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author BoringJimmy
 * @since 2021-06-07
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    // http://localhost:8001/eduservice/teacher/queryAll
    @GetMapping("queryAll")
    public R queryAllTeachers() {
        List<EduTeacher> teachers = teacherService.list(null);
        return R.ok().data("teachers", teachers);
    }

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R removeTeacherById(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if(b) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 查询讲师分页数据
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("pageTeacher/{pageNo}/{pageSize}")
    public R queryTeacherPage(@PathVariable long pageNo, @PathVariable long pageSize) {
        Page<EduTeacher> page = new Page<>(pageNo, pageSize);
        teacherService.page(page, null);
        long pageTotal = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("teacherPage", records).data("pageTotal", pageTotal);
    }

    /**
     * 根据条件查询讲师分页数据
     * @param pageNo
     * @param pageSize
     * @param wrapper
     * @return
     */
    @PostMapping("pageTeacherByCondition/{pageNo}/{pageSize}")
    public R queryTeacherByCondition(@PathVariable long pageNo, @PathVariable long pageSize, @RequestBody(required = false) TeacherQueryWrapper wrapper) {
        Page<EduTeacher> teacherPage = new Page<>(pageNo, pageSize);
        QueryWrapper<EduTeacher> qw = new QueryWrapper<>();

        String name = wrapper.getName();
        if(!StringUtils.isEmpty(name)) {
            qw.like("name", name);
        }

        Integer level = wrapper.getLevel();
        if(!StringUtils.isEmpty(level)) {
            qw.eq("level", level);
        }

        String begin = wrapper.getBegin();
        if(!StringUtils.isEmpty(begin)) {
            qw.ge("gmt_create", begin);
        }

        String end = wrapper.getEnd();
        if(!StringUtils.isEmpty(end)) {
            qw.le("gmt_create", end);
        }

        teacherService.page(teacherPage, qw);
        long pageTotal = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("teacherPage", records).data("pageTotal", pageTotal);
    }

    /**
     * 添加讲师
     * @param teacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        if(save) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 根据id查询讲师数据
     * @param id
     * @return
     */
    @GetMapping("queryTeacherById/{id}")
    public R queryTeacherById(@PathVariable long id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    /**
     * 更新讲师数据
     * @param teacher
     * @return
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if(flag) {
            return R.ok();
        }
        return R.error();
    }
}

