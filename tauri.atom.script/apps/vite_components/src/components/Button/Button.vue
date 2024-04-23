<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: 'my-button'
})
</script>
<script setup lang="ts">
import {ButtonProps} from './types.ts'
import {ref} from "vue"
import './style.css'
import Icon from '../Icon/Icon.vue'


withDefaults(defineProps<ButtonProps>(), {
  nativeType: 'button',
});

const _ref = ref<HTMLButtonElement>()
defineExpose({
  ref: _ref,
})
</script>

<template>
  <button
      ref="_ref"
      class="vk-button"
      :class="{
        [`vk-button--${type}`]: type,
        [`vk-button--${size}`]: size,
        'is-plain': plain,
        'is-round': round,
        'is-circle': circle,
        'is-disabled': disabled,
        'is-loading': loading,
      }"
      :disabled="disabled || loading"
      :autofocus="autofocus"
      :type="nativeType"
  >
    <Icon icon="spinner" spin v-if="loading" />
    <Icon :icon="icon" v-if="icon" />
    <span>
      <slot></slot>
    </span>
  </button>

</template>

<style scoped>

</style>