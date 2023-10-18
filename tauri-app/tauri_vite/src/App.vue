<script setup lang="ts">
// @ts-ignore
import Editor from "./components/Editor.vue";


</script>
<template>
  <p>
    <router-link to="/">To HelloWorld</router-link>
    <span style="padding: 0 3px;">|</span>
    <router-link to="/editor">To Editor</router-link>
  </p>
  <router-view></router-view>
</template>

<style scoped>
</style>
