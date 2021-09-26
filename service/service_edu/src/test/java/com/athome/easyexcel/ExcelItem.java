package com.athome.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelItem {

    @ExcelProperty(value = "学生编号", index = 0)
    private String id;

    @ExcelProperty(value = "学生姓名", index = 1)
    private String name;

    public ExcelItem(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
