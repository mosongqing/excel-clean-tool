




# 🧹 表格数据清洗工具（Excel Cleaner）

一个基于 **Spring Boot + Vue3 + TypeScript** 开发的前后端分离数据清洗工具，支持上传 Excel 表格、自动进行缺失值填补与重复项删除，并提供清洗后文件下载功能。适合数据预处理、表格治理、后台管理等使用场景。

---

## 🔧 技术栈

- **前端**：Vue3 + TypeScript + Axios + Element Plus
- **后端**：Spring Boot + EasyExcel + Java 21
- **数据库（可选）**：MySQL（用于记录上传历史或用户信息）

---

## ✨ 核心功能

- ✅ 上传 Excel 文件（.xlsx 格式）
- ✅ 自动检测缺失字段并用中位数填充
- ✅ 自动去除重复项（支持按列去重）
- ✅ 清洗结果一键下载
- ✅ 提供清洗耗时分析与日志查看
- ✅ 本地部署简单，轻量快速

---

## 📦 项目结构

```

excel-cleaner/
├── backend/     # Spring Boot 项目，处理上传、清洗与下载逻辑
├── frontend/    # Vue3 + TS 前端页面，支持文件上传与下载交互

````

---

## 🚀 快速启动

### 启动后端：

```bash
cd backend
mvn spring-boot:run
````

### 启动前端：

```bash
cd frontend
npm install
npm run dev
```

---

## 📷 页面预览（建议添加截图）
![dfe9f1a7-ec9b-452b-b67d-5fa5e58fbe5e](https://github.com/user-attachments/assets/cf150826-72d4-4e4b-9331-a0d1f4a682e3)
![11e37caa-07bc-4db4-9e34-61d9de9fe745](https://github.com/user-attachments/assets/585af4e7-dd0a-43d8-8299-c77942c7f4aa)
![ca731370-e167-4ae7-be5a-6999298f77f8](https://github.com/user-attachments/assets/e89682b8-90c8-40d2-b33a-d8153883db53)



---

## 📬 联系方式

* QQ / 微信：1926412875
* 项目作者主页：[https://momosongqing.asia](https://momosongqing.asia)

---

> 欢迎 Star ⭐、Fork 🔁，期待你的建议与贡献！

```


