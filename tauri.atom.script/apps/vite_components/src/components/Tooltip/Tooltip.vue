<script setup lang="ts">
import {computed, onUnmounted, reactive, ref, watch} from "vue";
import type {TooltipProps, TooltipEmits, TooltipInstance} from "./types.ts";
import {createPopper, Instance} from "@popperjs/core";
import useClickOutside from "@/hooks/useClickOutside.ts";


const props = withDefaults(defineProps<TooltipProps>(), {
  placement: 'bottom',
  trigger: 'hover',
  transition: 'fade',
})
const emits = defineEmits<TooltipEmits>()
const isOpen = ref(false)
const popperNode = ref<HTMLElement>()
const triggerNode = ref<HTMLElement>()
const popperContainerNode =  ref<HTMLElement>()
let popperInstance: null | Instance = null
let events: Record<string, any> = reactive({})
let outerEvents: Record<string, any> = reactive({})
const popperOptions = computed(() => {
  return {
    placement: props.placement,
    ...props.popperOptions,
  }
})


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
  if (props.trigger === 'click' && isOpen.value && !props.manual) {
    close()
  }
})

if (!props.manual) {
  attachEvents()
}

watch(() => props.manual, (isManual) => {
  if (isManual) {
    events = {}
    outerEvents = {}
  } else {
    attachEvents()
  }
})

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
      popperInstance = createPopper(triggerNode.value, popperNode.value, popperOptions.value)
    } else {
      popperInstance?.destroy()
    }
  }
}, { flush: 'post' })

onUnmounted(() => {
  popperInstance?.destroy()
})

defineExpose<TooltipInstance>({
  'show': open,
  'hide': close,
})
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

  <Transition :name="transition">
    <div
        v-if="isOpen"
        ref="popperNode"
        class="vk-tooltip_popper"
    >
      <slot name="content">
        {{ content }}
      </slot>
    </div>
  </Transition>

</div>

</template>

<style scoped>

</style>