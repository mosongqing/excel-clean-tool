package com.example.sortmonitor.util;
import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    // 读取 Excel 并返回对象列表
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        return EasyExcel.read(inputStream)
                .head(clazz)
                .sheet()
                .doReadSync();
    }

    // 写入 Excel 文件（到 File 对象）
    public static <T> void writeExcel(File file, List<T> data, Class<T> clazz, String sheetName) {
        EasyExcel.write(file, clazz)
                .sheet(sheetName)
                .doWrite(data);
    }

    // 写入 Excel 到 OutputStream（比如：响应流下载）
    public static <T> void writeExcel(OutputStream outputStream, List<T> data, Class<T> clazz, String sheetName) {
        EasyExcel.write(outputStream, clazz)
                .sheet(sheetName)
                .doWrite(data);
    }
}
