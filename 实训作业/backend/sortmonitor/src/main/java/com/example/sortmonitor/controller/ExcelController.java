package com.example.sortmonitor.controller;

import com.alibaba.excel.EasyExcel;
import com.example.sortmonitor.model.StudentInfo;
import com.example.sortmonitor.service.ExcelCleanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelCleanService cleanService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_cleaned.xlsx";

        // 1. 读取 Excel
        List<StudentInfo> raw = EasyExcel.read(file.getInputStream())
                .head(StudentInfo.class)
                .sheet()
                .doReadSync();

        // 2. 清洗数据
        List<StudentInfo> cleaned = cleanService.cleanData(raw);

        // 3. 写出到新 Excel 文件
        File outFile = new File("cleaned/" + filename);
        outFile.getParentFile().mkdirs();
        EasyExcel.write(outFile, StudentInfo.class).sheet("清洗结果").doWrite(cleaned);

        return ResponseEntity.ok(filename);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        File file = new File("cleaned/" + fileName);
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
