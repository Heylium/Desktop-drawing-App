<script setup lang="ts">
import type {MessageProps} from "@/components/Message/types.ts";
import RenderVNode from "@/components/Common/RenderVNode.ts";
import VkIcon from "@/components/Icon/Icon.vue";
import {onMounted, ref} from "vue";

const props = withDefaults(defineProps<MessageProps>(), {
  type: 'info',
  duration: 3000,
})
const visible = ref(false)
function startTimer() {
  if (props.duration === 0) {
    return
  }
  setTimeout(() => {
    visible.value = false
  }, props.duration)
}
onMounted(() => {
  visible.value = true
  startTimer()
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
      role="alert"
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