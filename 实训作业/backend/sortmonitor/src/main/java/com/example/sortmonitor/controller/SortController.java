package com.example.sortmonitor.controller;

import com.example.sortmonitor.model.SortResult;
import com.example.sortmonitor.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sort-time")
public class SortController {

    @Autowired
    private SortService sortService;

    @PostMapping
    public SortResult sortAndMeasure(@RequestBody SortRequest request) {
        return sortService.sort(request.getAlgorithm(), request.getSize());
    }

    static class SortRequest {
        private String algorithm;
        private int size;

        // Getters and Setters
        public String getAlgorithm() { return algorithm; }
        public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
    }
}
