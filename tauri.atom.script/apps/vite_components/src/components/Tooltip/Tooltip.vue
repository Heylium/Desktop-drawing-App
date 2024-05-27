<script setup lang="ts">
import {ref, watch} from "vue";
import type {TooltipProps, TooltipEmits} from "./types.ts";
import {createPopper, Instance} from "@popperjs/core";


const props = withDefaults(defineProps<TooltipProps>(), {
  placement: 'bottom',
})
const emits = defineEmits<TooltipEmits>()
const isOpen = ref(false)
const popperNode = ref<HTMLElement>()
const triggerNode = ref<HTMLElement>()
let popperInstance: null | Instance = null
const togglePopper = () => {
  isOpen.value = !isOpen.value
  emits('visible-change', isOpen.value)
}
watch(isOpen, (newValue) => {
  if (newValue) {
    if (triggerNode.value && popperNode.value) {
      popperInstance = createPopper(triggerNode.value, popperNode.value, {
        placement: props.placement,
      })
    } else {
      popperInstance?.destroy()
    }
  }
}, { flush: 'post' })

</script>

<template>
<div
  class="vk-tooltip"
>
  <div
      ref="triggerNode"
      class="vk-tooltip__trigger"
      @click="togglePopper"
  >
    <slot></slot>
  </div>
  <div
      v-if="isOpen"
      ref="popperNode"
      class="vk-tooltip_popper"
  >
    <slot name="content">
      {{ content }}
    </slot>
  </div>

</div>

</template>

<style scoped>

</style>