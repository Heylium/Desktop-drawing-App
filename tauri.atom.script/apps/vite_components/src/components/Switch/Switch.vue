<script setup lang="ts">
import {SwitchEmits, SwitchProps} from "./types.ts";
import {computed, onMounted, ref, watch} from "vue";

defineOptions({
  name: 'VkSwitch',
  inheritAttrs: false
})
const props = withDefaults(defineProps<SwitchProps>(), {
  activeValue: true,
  inactiveValue: false
})
const emits = defineEmits<SwitchEmits>()

const innerValue = ref(props.modelValue)
const input = ref<HTMLInputElement>()
// 现在是否被选中
const checked = computed(() => innerValue.value === props.activeValue)
const switchValue = () => {
  if (props.disabled) return
  const newValue = checked.value ? props.inactiveValue : props.activeValue
  innerValue.value = newValue
  emits('update:modelValue', newValue)
  emits('change', newValue)
}
onMounted(() => {
  input.value!.checked = checked.value
})
watch(checked, (val) => {
  input.value!.checked = val
})
watch(() => props.modelValue, (newValue) => {
  innerValue.value = newValue
})
</script>

<template>
  <div
      class="vk-switch"
      :class="{
    [`vk-switch--${size}`]: size,
    'is-disabled': disabled,
    'is-checked': checked
  }"
      @click="switchValue"
  >
    <input
        class="vk-switch__input"
        type="checkbox"
        role="switch"
        ref="input"
        :name="name"
        :disabled="disabled"
        @keydown.enter="switchValue"
    />
    <div class="vk-switch__core">
      <div class="vk-switch__core-inner">
      <span v-if="activeText || inactiveText" class="vk-switch__core-inner-text">
        {{checked ? activeText : inactiveText}}
      </span>
      </div>
      <div class="vk-switch__core-action">
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>