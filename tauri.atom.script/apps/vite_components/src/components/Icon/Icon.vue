<script setup lang="ts">
import {  omit } from "lodash-es";
import {IconProps} from "./types.ts";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";


defineOptions({
  name: 'VkIcon',
  inheritAttrs: false,
})

const props = defineProps<IconProps>()
const filteredProps = omit(props, ['type', 'color'])


</script>

<template>
  <i class="vk-icon" :class="{[`vk-icon--${type}`] : type}">
    <font-awesome-icon v-bind="filteredProps"/>
  </i>

</template>

<style scoped>

</style>