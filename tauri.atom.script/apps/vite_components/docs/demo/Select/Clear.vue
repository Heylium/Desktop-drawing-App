<script setup lang="ts">
import { ref } from 'vue'
import VkSelect from '../../../src/components/Select/Select.vue'
const test = ref('1')
const options2 = [
  { label: 'hello', value: '1' },
  { label: 'xyz', value: '2' },
  { label: 'testing', value: '3' },
  { label: 'check', value: '4', disabled: true }
]

</script>

<template>
  <VkSelect v-model="test" placeholder="可清空选择器，请选择" :options="options2" clearable />
</template>

<style scoped>

</style>