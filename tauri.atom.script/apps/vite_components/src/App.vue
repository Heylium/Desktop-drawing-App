<script setup lang="ts">
import MyButton from "./components/Button/Button.vue";
import Collapse from "./components/Collapse/Collapse.vue";
import Item from "./components/Collapse/CollapseItem.vue";
import {onMounted, ref} from "vue";
import type {ButtonInstance} from "./components/Button/types.ts";

const buttonRef = ref<ButtonInstance | null>(null)
onMounted(() => {
  console.log(`buttonRef:`, buttonRef.value?.ref)
})
</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>


  <main>
    <my-button type="primary" plain ref="buttonRef">Defined Button</my-button>
    <my-button plain >Plain</my-button>
    <my-button round >Round</my-button>
    <my-button circle >Circle</my-button>
    <my-button disabled >Disabled</my-button>

    <Collapse>
      <Item name="a">
        <template #title>
          <h1>nice title</h1>
        </template>
        <h1>headline title</h1>
        <div> this is content area </div>
      </Item>
      <Item name="b" title="nice title b item b">
        <div>this is area b</div>
      </Item>
      <Item name="c" title="nice title c " disabled>
        <div> this is area c </div>
      </Item>
    </Collapse>
  </main>

  <a href="#">The link</a>
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
