<template>
  <div class="excel-uploader">
    <h2>📤 Excel 数据清洗</h2>

    <input type="file" @change="handleFileChange" accept=".xlsx" />
    <button :disabled="!selectedFile || loading" @click="uploadFile">
      {{ loading ? '上传中...' : '上传并清洗' }}
    </button>

    <div v-if="fileName" class="result">
      ✅ 清洗成功，文件名：<strong>{{ fileName }}</strong><br />
      <a :href="downloadUrl" download target="_blank">
        📥 点击下载清洗后的文件
      </a>
    </div>

    <div v-if="error" class="error">
      ❌ {{ error }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import axios from 'axios'

const selectedFile = ref<File | null>(null)
const fileName = ref('')
const loading = ref(false)
const error = ref('')

const handleFileChange = (event: Event) => {
  const files = (event.target as HTMLInputElement).files
  if (files && files.length > 0) {
    selectedFile.value = files[0]
    fileName.value = ''
    error.value = ''
  }
}

const uploadFile = async () => {
  if (!selectedFile.value) return

  loading.value = true
  error.value = ''
  const formData = new FormData()
  formData.append('file', selectedFile.value)

  try {
    const res = await axios.post('http://localhost:8080/api/excel/upload', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
    fileName.value = res.data
  } catch (err: any) {
    error.value = err?.response?.data?.message || '上传失败，请重试'
  } finally {
    loading.value = false
  }
}

const downloadUrl = computed(() =>
  fileName.value
    ? `http://localhost:8080/api/excel/download/${encodeURIComponent(fileName.value)}`
    : ''
)
</script>

<style scoped>
.excel-uploader {
  padding: 1.5rem;
  max-width: 500px;
  margin: auto;
  border: 2px dashed #aaa;
  border-radius: 12px;
  text-align: center;
}

button {
  margin-top: 10px;
  padding: 6px 14px;
  font-weight: bold;
}

.result {
  margin-top: 15px;
  color: green;
}

.error {
  margin-top: 10px;
  color: red;
}
</style>
