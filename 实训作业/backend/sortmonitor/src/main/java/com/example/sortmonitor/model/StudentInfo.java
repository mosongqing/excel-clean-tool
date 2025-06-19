package com.example.sortmonitor.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentInfo {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("学号")
    private String studentId;

    @ExcelProperty("数学成绩")
    private Double mathScore;

    @ExcelProperty("英语成绩")
    private Double englishScore;
}
