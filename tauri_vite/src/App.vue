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
<!--  <div>-->
<!--    <a href="https://vitejs.dev" target="_blank">-->
<!--      <img src="/vite.svg" class="logo" alt="Vite logo" />-->
<!--    </a>-->
<!--    <a href="https://vuejs.org/" target="_blank">-->
<!--      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />-->
<!--    </a>-->
<!--  </div>-->
<!--  <Editor/>-->
<!--  <hello-world></hello-world>-->

</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
