<script setup lang="ts">
import type {DropDownProps, DropDownInstance, DropDownEmits, MenuOption} from "@/components/DropDown/types.ts";
import VkTooltip from "@/components/Tooltip/Tooltip.vue";
import {Ref, ref} from "vue";
import {TooltipInstance} from "@/components/Tooltip/types.ts";


const props = defineProps<DropDownProps>()
const emits = defineEmits<DropDownEmits>()
const visibleChange = (e: boolean): void => {
  emits('visible-change', e)
}
const tooltipRef = ref() as Ref<TooltipInstance>

const itemClick = (e: MenuOption) => {
  if (e.disabled) {
    return
  }
  emits('select', e)
}

defineExpose<DropDownInstance>({
  show: tooltipRef.value?.show,
  hide: tooltipRef.value?.hide,
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
            >{{item.label}}</li>
          </template>
        </ul>
      </template>
    </VkTooltip>
  </div>

</template>

<style scoped>

</style>