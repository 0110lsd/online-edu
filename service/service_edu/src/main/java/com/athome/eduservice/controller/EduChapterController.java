package com.athome.eduservice.controller;


import com.athome.commonutils.R;
import com.athome.eduservice.entity.EduChapter;
import com.athome.eduservice.entity.chapter.ChapterVo;
import com.athome.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Boring Jimmy
 * @since 2021-06-17
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 通过课程id查询该课程所有章节
     * @param courseId
     * @return
     */
    @GetMapping("getChapterList/{courseId}")
    public R getChapterList(@PathVariable String courseId) {

        List<ChapterVo> chapterList = chapterService.getChaptersByCourseId(courseId);
        return R.ok().data("chapters", chapterList);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 通过章节id获取章节信息
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("eduChapter", eduChapter);
    }

    /**
     * 更新章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping("updateChapterInfo")
    public R updateChapterInfo(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 通过章节id移除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R removeChapterById(@PathVariable String chapterId) {
        boolean del = chapterService.deleteById(chapterId);
        if(del) {
            return R.ok();
        }
        return R.error();
    }
}

