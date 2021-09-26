package com.athome.eduservice.service;

import com.athome.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author BoringJimmy
 * @since 2021-06-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> pageFrontTeacher(long pageNo, long pageSize);
}
