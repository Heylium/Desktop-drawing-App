<script setup lang="ts">
import {ref, provide, watch} from "vue"
import type {NameType, CollapseProps, CollapseEmits} from "./types.ts";
import {collapseContextKey} from "./types.ts";


defineOptions({
  name: 'VkCollapse'
})
const props = defineProps<CollapseProps>()
const emits = defineEmits<CollapseEmits>()
const activeNames = ref<NameType[]>(props.modelValue)

// watching active names change
watch(() => props.modelValue, () => {
  activeNames.value = props.modelValue
})
if (props.accordion && activeNames.value.length > 1) {
  console.warn(`accordion mode should only have one active item`)
}

const handleItemClick = (item: NameType) => {
  if (props.accordion) {
    activeNames.value = [ activeNames.value[0] === item ? '' : item ]
  } else {
    const index = activeNames.value.indexOf(item)
    if (index > -1) {
      activeNames.value.splice(index, 1)
    } else {
      activeNames.value.push(item)
    }
  }
  emits('update:modelValue', activeNames.value)
  emits('change', activeNames.value)
}

provide(collapseContextKey, {
  activeNames,
  handleItemClick,
})
</script>

<template>
  <div
    class="vk-collapse"
  >
    <slot></slot>
  </div>

</template>

<style scoped>

</style>