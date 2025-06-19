package com.example.sortmonitor.model;

public class SortResult {
    private String algorithm;
    private int size;
    private double timeMillis;

    public SortResult(String algorithm, int size, double timeMillis) {
        this.algorithm = algorithm;
        this.size = size;
        this.timeMillis = timeMillis;
    }

    // Getters
    public String getAlgorithm() { return algorithm; }
    public int getSize() { return size; }
    public double getTimeMillis() { return timeMillis; }
}
