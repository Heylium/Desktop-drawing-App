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
  let _activeNames = [...activeNames.value]
  if (props.accordion) {
    _activeNames = [ activeNames.value[0] === item ? '' : item ]
    activeNames.value = _activeNames
  } else {
    const index = _activeNames.indexOf(item)
    if (index > -1) {
      _activeNames.splice(index, 1)
    } else {
      _activeNames.push(item)
    }
    activeNames.value = _activeNames
  }
  emits('update:modelValue', _activeNames)
  emits('change', _activeNames)
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