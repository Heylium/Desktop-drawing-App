<script setup lang="ts">
import { ref } from 'vue'
import Switch from '../../../src/components/Switch/Switch.vue'
const test = ref(false)
const test2 = ref(false)
</script>

<template>
  正常：<Switch v-model="test" /> <br/>
  禁用：<Switch v-model="test2" disabled/>

</template>

<style scoped>

</style>