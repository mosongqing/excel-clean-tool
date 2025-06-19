from ace_tools import code_update

code_update(name="SortPerformanceView.vue", changes="""
<!-- 安装依赖：pnpm add echarts -->
<template>
  <div class="sort-page">
    <h2 class="title">排序性能测试</h2>

    <!-- ⏱️ 表单区 -->
    <div class="form">
      <label for="algorithm">选择排序算法：</label>
      <select v-model="form.algorithm" id="algorithm">
        <option value="bubble">冒泡排序</option>
        <option value="quick">快速排序</option>
        <option value="merge">归并排序</option>
      </select>

      <label for="size">输入数组大小：</label>
      <input
        type="number"
        v-model.number="form.size"
        id="size"
        placeholder="如 1000、5000、10000"
      />

      <button :disabled="loading" @click="submit">
        {{ loading ? '测试中...' : '提交测试' }}
      </button>
    </div>

    <!-- 🎯 测试结果 -->
    <div v-if="result" class="result" :style="{ color: resultColor }">
      <h3>测试结果：</h3>
      <p>算法：{{ result?.algorithm }}</p>
      <p>数组大小：{{ result?.size }}</p>
      <p>
        执行时间：{{ result?.timeMillis?.toFixed(3) }} 毫秒
        <span v-if="isFastest" style="font-weight: bold; color: gold">🏅 最快！</span>
      </p>
    </div>

    <!-- 📊 折线图展示 -->
    <div ref="chartRef" class="chart" style="height: 300px; margin-top: 30px" />

    <!-- 💾 测试历史记录表格 -->
    <div v-if="history.length > 0" class="history">
      <h3>历史记录</h3>
      <table>
        <thead>
          <tr>
            <th>算法</th>
            <th>数组大小</th>
            <th>耗时（ms）</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, i) in history" :key="i">
            <td>{{ item.algorithm }}</td>
            <td>{{ item.size }}</td>
            <td>{{ item.timeMillis.toFixed(3) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <ExcelUploader />
</template>

<script lang="ts" setup>
import ExcelUploader from './components/ExcelUploader.vue'
import { ref, computed, onMounted, watch } from 'vue'
import axios from 'axios'
import * as echarts from 'echarts'

interface SortForm {
  algorithm: string
  size: number
}
interface SortResult {
  algorithm: string
  size: number
  timeMillis: number
}

const form = ref<SortForm>({ algorithm: 'quick', size: 1000 })
const result = ref<SortResult | null>(null)
const loading = ref(false)
const history = ref<SortResult[]>([])
const chartRef = ref<HTMLElement>()
let chart: echarts.ECharts | null = null

// 🌈 颜色映射
const algorithmColors = {
  bubble: '#007bff',
  quick: '#28a745',
  merge: '#6f42c1',
}
const resultColor = computed(() =>
  result.value ? algorithmColors[result.value.algorithm as keyof typeof algorithmColors] : '#000'
)
const fastestThreshold = 0.5
const isFastest = computed(() => result.value?.timeMillis < fastestThreshold)

// ⏱️ 提交逻辑
const submit = async () => {
  loading.value = true
  try {
    const res = await axios.post('http://localhost:8080/api/sort-time', form.value)
    result.value = res.data
    history.value.push(res.data)
    updateChart()
  } catch (error) {
    alert('请求失败，请检查后端是否启动！')
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 📊 初始化图表
onMounted(() => {
  if (chartRef.value) {
    chart = echarts.init(chartRef.value)
    updateChart()
  }
})

watch(history, updateChart)

function updateChart() {
  if (!chart) return

  const sizes = history.value.map((h) => h.size)
  const seriesMap: Record<string, number[]> = {
    bubble: [],
    quick: [],
    merge: [],
  }

  history.value.forEach((item) => {
    seriesMap[item.algorithm].push(item.timeMillis)
  })

  chart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['bubble', 'quick', 'merge'] },
    xAxis: { type: 'category', data: sizes },
    yAxis: { type: 'value', name: '耗时(ms)' },
    series: ['bubble', 'quick', 'merge'].map((name) => ({
      name,
      type: 'line',
      data: seriesMap[name],
      color: algorithmColors[name as keyof typeof algorithmColors],
    })),
  })
}
</script>

<style scoped>
.sort-page {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
  border-radius: 10px;
  background: #f9f9f9;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}
.title {
  text-align: center;
  margin-bottom: 20px;
}
.form label {
  display: block;
  margin-top: 10px;
  font-weight: bold;
}
.form select,
.form input {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}
button:disabled {
  background-color: #a0cfff;
  cursor: not-allowed;
}
.result {
  margin-top: 20px;
  padding: 15px;
  background: #e0f7fa;
  border-radius: 5px;
}
.chart {
  background: #fff;
  border-radius: 8px;
}
.history {
  margin-top: 30px;
}
.history table {
  width: 100%;
  border-collapse: collapse;
}
.history th,
.history td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: center;
}

/* 🌙 深色模式适配 */
@media (prefers-color-scheme: dark) {
  .sort-page {
    background: #1e1e1e;
    color: #fff;
  }
  .form select,
  .form input {
    background-color: #333;
    color: #fff;
    border: 1px solid #888;
  }
  .result,
  .chart {
    background-color: #333;
    border: 1px solid #666;
  }
  .history table {
    background: #222;
    color: #eee;
  }
  .history th,
  .history td {
    border-color: #555;
  }
}
</style>
""")
