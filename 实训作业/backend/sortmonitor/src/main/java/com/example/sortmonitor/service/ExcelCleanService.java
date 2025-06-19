package com.example.sortmonitor.service;

import com.example.sortmonitor.model.StudentInfo;
import com.example.sortmonitor.service.ExcelCleanService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ExcelCleanService {

    public List<StudentInfo> cleanData(List<StudentInfo> rawList) {
        // 1. 去重：按 studentId 保留第一条
        List<StudentInfo> deduplicated = rawList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(StudentInfo::getStudentId, Function.identity(), (a, b) -> a),
                        map -> new ArrayList<>(map.values())
                ));

        // 2. 计算中位数
        Double mathMedian = getMedian(deduplicated.stream()
                .map(StudentInfo::getMathScore)
                .collect(Collectors.toList()));
        Double engMedian = getMedian(deduplicated.stream()
                .map(StudentInfo::getEnglishScore)
                .collect(Collectors.toList()));

        // 3. 填充缺失值
        for (StudentInfo student : deduplicated) {
            if (student.getMathScore() == null) student.setMathScore(mathMedian);
            if (student.getEnglishScore() == null) student.setEnglishScore(engMedian);
        }

        return deduplicated;
    }

    private Double getMedian(List<Double> values) {
        List<Double> clean = values.stream().filter(Objects::nonNull).sorted().collect(Collectors.toList());
        if (clean.isEmpty()) return 0.0;
        int mid = clean.size() / 2;
        return (clean.size() % 2 == 0)
                ? (clean.get(mid - 1) + clean.get(mid)) / 2
                : clean.get(mid);
    }
}
