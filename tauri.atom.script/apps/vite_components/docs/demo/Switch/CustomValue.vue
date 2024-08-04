<script setup lang="ts">
import { ref } from 'vue'
import Switch from '../../../src/components/Switch/Switch.vue'
const test = ref('right')
</script>

<template>
  <Switch v-model="test" activeValue="right" inactiveValue="wrong"/>
  <h4>model-value: {{test}}</h4>
</template>

<style scoped>

</style>