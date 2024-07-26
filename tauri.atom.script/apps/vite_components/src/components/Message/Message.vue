<script setup lang="ts">
import type {MessageProps} from "../Message/types.ts";
import RenderVNode from "../Common/RenderVNode.ts";
import VkIcon from "../Icon/Icon.vue";
import {computed, nextTick, onMounted, ref} from "vue";
import {getLastBottomOffset} from "./method.ts";
import useEventListeners from "../../hooks/useEventListener.ts";

const props = withDefaults(defineProps<MessageProps>(), {
  type: 'info',
  duration: 3000,
  offset: 20,
  transitionName: 'fade-up',
})
const visible = ref(false)
const messageRef = ref<HTMLDivElement>()
// calculate offset height
const height = ref(0)
// last message offset
const lastOffset = computed(() => getLastBottomOffset(props.id))
// current message top
const topOffset = computed(() => props.offset + lastOffset.value)
// bottomOffset for next message
const bottomOffset = computed(() => height.value + topOffset.value)
const cssStyle = computed(() => ({
  top: topOffset.value + "px",
  zIndex: props.zIndex,
}))
let timer: any
function startTimer() {
  if (props.duration === 0) {
    return
  }
  timer = setTimeout(() => {
    visible.value = false
  }, props.duration)
}
function clearTimer() {
  clearTimeout(timer)
}

onMounted(async () => {
  visible.value = true
  startTimer()
  await nextTick()
  // height.value = messageRef.value!.getBoundingClientRect().height
})

function keydown(e: Event) {
  const event = e as KeyboardEvent
  if (event.code === 'Escape') {
    visible.value = false
  }
}
useEventListeners(document, 'keydown', keydown)
// watch(visible, (newVal) => {
//   if (!newVal) {
//     props.onDestroy()
//   }
// })
function destroyComponent() {
  props.onDestroy()
}
function updateHeight() {
  height.value = messageRef.value!.getBoundingClientRect().height
}
defineExpose({
  bottomOffset,
  visible,
})
</script>

<template>
  <Transition
      :name="transitionName"
      @after-leave="destroyComponent"
      @enter="updateHeight"
  >
    <div
        class="vk-message"
        v-show="visible"
        :class="{
        [`vk-message--${type}`]: type,
        'is-close': showClose,
      }"
        :style="cssStyle"
        role="alert"
        ref="messageRef"
        @mouseenter="clearTimer"
        @mouseleave="startTimer"
    >
      <div class="vk-message__content">
        <slot>
          <RenderVNode :v-node="message" v-if="message" />
        </slot>
      </div>

      <div class="vk-message__close" v-if="showClose">
        <VkIcon @click.stop="$event => visible = false" icon="xmark" />
      </div>
    </div>
  </Transition>

</template>

<style scoped>

</style>