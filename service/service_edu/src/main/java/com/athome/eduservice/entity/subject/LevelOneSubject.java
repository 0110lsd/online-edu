package com.athome.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LevelOneSubject {

    private String id;
    private String title;

    private List<LevelTwoSubject> children = new ArrayList<>();
}
