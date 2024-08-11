<script setup lang="ts">
import { ref } from 'vue'
import VkSelect from "../../../src/components/Select/Select.vue";
const test = ref('')
const options2 = [
  { label: 'hello', value: '1' },
  { label: 'xyz', value: '2' },
  { label: 'testing', value: '3' },
  { label: 'check', value: '4', disabled: true }
]

</script>

<template>
  <VkSelect
      v-model="test"
      placeholder="可以过滤的选择器，请输入"
      :options="options2"
      filterable
  />

</template>

<style scoped>

</style>