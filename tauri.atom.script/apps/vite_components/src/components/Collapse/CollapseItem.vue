<script setup lang="ts">
import {CollapseItemProps} from "./types.ts";

defineOptions({
  name: 'VkCollapseItem',
})
defineProps<CollapseItemProps>();

</script>

<template>
<div
  class="vk-collapse-item"
  :class="{
      'is-disabled': disabled
    }"
>
  <div class="vk-collapse-item__header" :id="`item-header-${name}`">
    <slot name="title">{{title}}</slot>
  </div>

  <div class="vk-collapse-item__content" :id="`item-content-${name}`">
    <slot></slot>
  </div>
</div>
</template>

<style scoped>

</style>