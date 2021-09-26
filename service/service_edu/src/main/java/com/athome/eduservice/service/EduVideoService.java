package com.athome.eduservice.service;

import com.athome.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
