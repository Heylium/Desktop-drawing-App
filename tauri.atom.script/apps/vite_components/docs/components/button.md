---
title: Button | V-Element
description: Button component document
---

# Button
常用的操作按钮

## 基础用法
使用 `type`、 `plain`、 `round`和`circle`来定义按钮的样式。

<script setup>
import {ref} from 'vue';
const count = ref(0)
</script>
Current count is: {{count}}

<button @click="count++">Add</button>
