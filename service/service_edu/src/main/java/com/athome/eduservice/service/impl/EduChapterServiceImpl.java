package com.athome.eduservice.service.impl;

import com.athome.eduservice.entity.EduChapter;
import com.athome.eduservice.entity.EduVideo;
import com.athome.eduservice.entity.chapter.ChapterVo;
import com.athome.eduservice.entity.chapter.VideoVo;
import com.athome.eduservice.mapper.EduChapterMapper;
import com.athome.eduservice.service.EduChapterService;
import com.athome.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChaptersByCourseId(String courseId) {
        List<ChapterVo> res = new ArrayList<>();
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(wrapperVideo);

        for(EduChapter chapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            res.add(chapterVo);
            String chapterId = chapter.getId();
            List<VideoVo> children = new ArrayList<>();
            for(EduVideo video : videoList) {
                if(video.getChapterId().equals(chapterId)) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    children.add(videoVo);
                }
            }
            chapterVo.setChildren(children);
        }
        return res;
    }

    @Override
    public boolean deleteById(String chapterId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("chapter_id", chapterId);
        int count = videoService.count(wrapperVideo);
        if(count > 0) {
            throw new RuntimeException("章节含有视频内容，不能删除");
        }
        int rows = baseMapper.deleteById(chapterId);
        return rows > 0;
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
