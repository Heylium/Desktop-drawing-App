<script setup lang="ts">
import type {DropDownProps, DropDownInstance, DropDownEmits} from "@/components/DropDown/types.ts";
import VkTooltip from "@/components/Tooltip/Tooltip.vue";



const props = defineProps<DropDownProps>()
const emits = defineEmits<DropDownEmits>()
const visibleChange = (e: boolean): void => {
  emits('visible-change', e)
}


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
                :class="{
                  'is-disabled': item.disabled,
                  'is-divided': item.divided,
                }"
                :id="item.key"
            >{{item.label}}</li>
          </template>
        </ul>
      </template>
    </VkTooltip>
  </div>

</template>

<style scoped>

</style>