<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
  name: 'my-button'
})
</script>
<script setup lang="ts">
import {ButtonProps} from './types.ts'

withDefaults(defineProps<ButtonProps>(), {
  nativeType: 'button',
})
</script>

<template>
  <button
      class="my-button"
      :class="{
        [`my-button--${type}`]: type,
        [`my-button--${size}`]: size,
        'is-plain': plain,
        'is-round': round,
        'is-circle': circle,
        'is-disabled': disabled,
      }"
      :disabled="disabled"
      :autofocus="autofocus"
      :type="nativeType"
  >
    <span>
      <slot></slot>
    </span>
  </button>

</template>

<style scoped>

</style>