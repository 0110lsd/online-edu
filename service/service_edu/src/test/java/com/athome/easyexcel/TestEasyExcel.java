package com.athome.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {

        String path = "/Users/liusuodong/Desktop/out.xlsx";

        EasyExcel.write(path, ExcelItem.class).sheet("学生列表").doWrite(getData());
    }

    private static List<ExcelItem> getData() {
        List<ExcelItem> items = new ArrayList<>();
        ExcelItem ei = new ExcelItem("1", "Jack");
        ExcelItem ei2 = new ExcelItem("2", "Lucy");
        ExcelItem ei3 = new ExcelItem("3", "Lisa");
        ExcelItem ei4 = new ExcelItem("4", "Amy");
        ExcelItem ei5 = new ExcelItem("5", "Linda");
        ExcelItem ei6 = new ExcelItem("6", "Jimmy");
        items.add(ei);
        items.add(ei2);
        items.add(ei3);
        items.add(ei4);
        items.add(ei5);
        items.add(ei6);
        return items;
    }
}
