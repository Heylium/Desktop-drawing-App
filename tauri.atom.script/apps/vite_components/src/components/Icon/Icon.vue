<script setup lang="ts">
// import type { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {IconProps} from "./types.ts";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";


defineOptions({
  name: 'VkIcon',
  inheritAttrs: false,
})

defineProps<IconProps>()

</script>

<template>
  <i class="vk-icon">
    <font-awesome-icon v-bind="$props"/>
  </i>

</template>

<style scoped>

</style>