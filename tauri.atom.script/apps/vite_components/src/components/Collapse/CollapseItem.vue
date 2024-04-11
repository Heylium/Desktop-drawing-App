<script setup lang="ts">
import {computed, inject} from 'vue'
import type {CollapseItemProps} from "./types.ts";
import { collapseContextKey } from "./types.ts";

defineOptions({
  name: 'VkCollapseItem',
})

const props = defineProps<CollapseItemProps>();

const collapseContext = inject(collapseContextKey)
const isActive = computed(() => collapseContext?.activeNames.value.includes(props.name))
const handleClick = () => {
  if (props.disabled) return
  collapseContext?.handleItemClick(props.name)
}
const transitionEvents: Record<string, (el: HTMLElement) => void> = {
  beforeEnter(el) {
    el.style.height = '0px'
  },
  enter(el) {
    el.style.height = `${el.scrollHeight}px`
  },
  afterEnter(el) {
    el.style.height = ``
  },
  beforeLeave(el) {
    el.style.height = `${el.scrollHeight}px`
  },
  leave(el) {
    el.style.height = '0px'
  },
  afterLeave(el) {
    el.style.height = ''
  }
}
</script>

<template>
<div
  class="vk-collapse-item"
  :class="{
      'is-disabled': disabled,
      'is-active': isActive,
    }"
>
  <div class="vk-collapse-item__header" :id="`item-header-${name}`" @click="handleClick">
    <slot name="title">{{title}}</slot>
  </div>
  <Transition name="fade" v-on="transitionEvents">
    <div class="vk-collapse-item__content" :id="`item-content-${name}`" v-show="isActive">
      <slot></slot>
    </div>
  </Transition>
</div>
</template>

<style scoped>
.vk-collapse-item__header {
  font-size: 30px;
}

</style>