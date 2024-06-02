<script setup lang="ts">
import {reactive, ref, watch} from "vue";
import type {TooltipProps, TooltipEmits} from "./types.ts";
import {createPopper, Instance} from "@popperjs/core";
import useClickOutside from "@/hooks/useClickOutside.ts";


const props = withDefaults(defineProps<TooltipProps>(), {
  placement: 'bottom',
  trigger: 'hover',

})
const emits = defineEmits<TooltipEmits>()
const isOpen = ref(false)
const popperNode = ref<HTMLElement>()
const triggerNode = ref<HTMLElement>()
const popperContainerNode =  ref<HTMLElement>()
let popperInstance: null | Instance = null
let events: Record<string, any> = reactive({})
let outerEvents: Record<string, any> = reactive({})
const togglePopper = () => {
  isOpen.value = !isOpen.value
  emits('visible-change', isOpen.value)
}

const open = () => {
  isOpen.value = true
  emits('visible-change', true)
}
const close = () => {
  isOpen.value = false
  emits('visible-change', false)
}
const attachEvents = () => {
  if (props.trigger === 'hover') {
    events['mouseenter'] = open
    events['mouseleave'] = close
  } else if (props.trigger === 'click') {
    events['click'] = togglePopper
  }
}

useClickOutside(popperContainerNode, () => {
  if (props.trigger === 'click' && isOpen.value) {
    close()
  }
})

attachEvents()

watch(() => props.trigger, (newTrigger, oldTrigger) => {
  if (newTrigger !== oldTrigger) {
    // clear events
    events = {}
    outerEvents = {}
    attachEvents()
  }
})

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
  v-on="outerEvents"
  ref="popperContainerNode"
>
  <div
      ref="triggerNode"
      class="vk-tooltip__trigger"
      v-on="events"
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