<script setup lang="ts">
import type {MessageProps} from "@/components/Message/types.ts";
import RenderVNode from "@/components/Common/RenderVNode.ts";
import VkIcon from "@/components/Icon/Icon.vue";
import {computed, nextTick, onMounted, ref, watch} from "vue";
import {getLastBottomOffset, getLastInstance} from "@/components/Message/method.ts";

const props = withDefaults(defineProps<MessageProps>(), {
  type: 'info',
  duration: 3000,
  offset: 20,
})
const visible = ref(false)
const messageRef = ref<HTMLDivElement>()
// calculate offset height
const height = ref(0)
// last message offset
const lastOffset = computed(() => getLastBottomOffset())
// current message top
const topOffset = computed(() => props.offset + lastOffset.value)
// bottomOffset for next message
const bottomOffset = computed(() => height.value - topOffset.value)
const cssStyle = computed(() => ({
  top: topOffset.value + "px",
}))
function startTimer() {
  if (props.duration === 0) {
    return
  }
  setTimeout(() => {
    visible.value = false
  }, props.duration)
}
onMounted(async () => {
  visible.value = true
  startTimer()
  await nextTick()
  height.value = messageRef.value!.getBoundingClientRect().height
})
watch(visible, (newVal) => {
  if (!newVal) {
    props.onDestroy()
  }
})
defineExpose({
  bottomOffset
})
</script>

<template>
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

</template>

<style scoped>
.vk-message {
  width: max-content;
  position: fixed;
  left: 50%;
  top: 20px;
  transform: translateX(-50%);
  border: 1px solid blue;
}

</style>