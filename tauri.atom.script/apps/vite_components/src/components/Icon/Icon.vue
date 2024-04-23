<script setup lang="ts">
import {  omit } from "lodash-es";
import {IconProps} from "./types.ts";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {computed} from "vue";


defineOptions({
  name: 'VkIcon',
  inheritAttrs: false,
})

const props = defineProps<IconProps>()
const filteredProps = computed(() => omit(props, ['type', 'color']))
const customStyles = computed(() => {
  return props.color ? { color: props.color} : {}
})

</script>

<template>
  <i class="vk-icon" :class="{[`vk-icon--${type}`] : type}" :style="customStyles" v-bind="$attrs">
    <font-awesome-icon v-bind="filteredProps"/>
  </i>

</template>

<style scoped>

</style>