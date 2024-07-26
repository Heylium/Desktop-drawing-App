<script setup lang="ts">
import type {DropdownProps, DropdownInstance, DropdownEmits, MenuOption} from "@/components/DropDown/types.ts";
import VkTooltip from "../Tooltip/Tooltip.vue";
import {ref} from "vue";
import {TooltipInstance} from "../Tooltip/types.ts";
import RenderVNode from "../Common/RenderVNode.ts";


defineOptions({
  name: 'VkDropDown',
})

const props = withDefaults(defineProps<DropdownProps>(), { hideAfterClick: true })
const emits = defineEmits<DropdownEmits>()
const tooltipRef = ref<TooltipInstance | null>(null)
const visibleChange = (e: boolean) => {
  emits('visible-change', e)
}

const itemClick = (e: MenuOption) => {
  if (e.disabled) {
    return
  }
  emits('select', e)
  if (props.hideAfterClick) {
    tooltipRef.value?.hide()
  }
}

defineExpose<DropdownInstance>({
  show: () => tooltipRef.value?.show(),
  hide: () => tooltipRef.value?.hide()
})
</script>

<template>
  <div
      class="vk-drop-down"
  >
    <VkTooltip
        :trigger="trigger"
        :placement="placement"
        :popper-options="popperOptions"
        :open-delay="openDelay"
        :close-delay="closeDelay"
        @visible-change="visibleChange"
        ref="tooltipRef"
    >
      <slot></slot>
      <template #content>
        <ul class="vk-dropdown__menu">
          <template v-for="item in menuOptions" :key="item.key">
            <li
                v-if="item.divided"
                role="separator"
                class="divided-placeholder"
            ></li>
            <li
                class="vk-dropdown__item"
                @click="itemClick(item)"
                :class="{
                  'is-disabled': item.disabled,
                  'is-divided': item.divided,
                }"
                :id="`dropdown-item-${item.key}`"
            >
              <RenderVNode :v-node="item.label" />
            </li>
          </template>
        </ul>
      </template>
    </VkTooltip>
  </div>

</template>

<style scoped>

</style>