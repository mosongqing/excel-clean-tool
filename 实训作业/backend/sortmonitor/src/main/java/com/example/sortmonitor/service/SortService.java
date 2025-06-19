package com.example.sortmonitor.service;

import com.example.sortmonitor.model.SortResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class SortService {

    /**
     * 核心方法：根据传入的算法名称和数据规模，生成随机数组并执行排序，统计排序耗时。
     * @param algorithm 排序算法名称（bubble, quick, merge）
     * @param size      数组大小
     * @return 排序结果，包括算法名称、数组大小、执行时间（毫秒）
     */
    public SortResult sort(String algorithm, int size) {
        int[] data = generateRandomArray(size);  // 生成随机数组
        long start = System.nanoTime();          // 记录开始时间（纳秒）

        // 根据参数选择对应的排序算法
        switch (algorithm.toLowerCase()) {
            case "bubble":
                bubbleSort(data);
                break;
            case "quick":
                quickSort(data, 0, data.length - 1);
                break;
            case "merge":
                mergeSort(data, 0, data.length - 1);
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        long end = System.nanoTime();            // 记录结束时间
        double timeMillis = (end - start) / 1_000_000.0; // 转换为毫秒

        return new SortResult(algorithm, size, timeMillis);
    }

    /**
     * 生成指定大小的随机整型数组
     * @param size 数组长度
     * @return 随机整数数组（值范围：0 到 size * 10）
     */
    private int[] generateRandomArray(int size) {
        Random random = new Random();
        return random.ints(size, 0, size * 10-1).toArray();
    }

    /**
     * 冒泡排序（Bubble Sort）
     * 时间复杂度 O(n²)，适用于小规模数据
     */
    private void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换相邻元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 快速排序（Quick Sort）
     * 平均时间复杂度 O(n log n)，基于分治
     */
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 获取分区点
            int pi = partition(arr, low, high);
            // 递归排序左右部分
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    /**
     * 快速排序的分区操作，选取最后一个元素为基准
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 基准元素
        int i = low - 1;       // 小于 pivot 的索引边界

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // 交换 arr[i] 和 arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // 最后将 pivot 放到正确位置
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    /**
     * 归并排序（Merge Sort）
     * 稳定排序，时间复杂度 O(n log n)
     */
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            // 分别对左右半部分排序
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            // 合并两部分
            merge(arr, left, mid, right);
        }
    }

    /**
     * 合并两个已排序的子数组
     */
    private void merge(int[] arr, int left, int mid, int right) {
        // 复制左右子数组
        int[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        int[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;

        // 合并两个有序子数组
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        // 复制剩余元素
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }
}
