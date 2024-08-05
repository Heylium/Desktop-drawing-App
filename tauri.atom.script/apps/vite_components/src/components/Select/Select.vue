<script setup lang="ts">
import type {SelectEmits, SelectProps} from "./types.ts";
import VkTooltip from "../Tooltip/Tooltip.vue";
import Input from "../Input/Input.vue";
import VkIcon from "../../App.vue";
import RenderVnode from '../Common/RenderVnode'

defineOptions({
  name: 'VkSelect',
})
const props = defineProps<SelectProps>()
const emits = defineEmits<SelectEmits>()

</script>

<template>
  <div
      class="vk-select"
      :class="{'is-disabled': disabled }"
      @click="toggleDropdown"
      @mouseenter="states.mouseHover = true"
      @mouseleave="states.mouseHover = false"
  >
    <VkTooltip
        placement="bottom-start"
        ref="tooltipRef"
        :popperOptions="popperOptions"
        @click-outside="controlDropdown(false)"
        manual
    >
      <Input
          v-model="states.inputValue"
          :disabled="disabled"
          :placeholder="filteredPlaceholder"
          ref="inputRef"
          :readonly="!filterable || !isDropdownShow"
          @input="debouceOnFilter"
          @keydown="handleKeydown"
      >
        <template #suffix>
          <VkIcon
              icon="circle-xmark"
              v-if="showClearIcon"
              class="vk-input__clear"
              @mousedown.prevent="NOOP"
              @click.stop="onClear"
          />

          <VkIcon
              v-else
              icon="angle-down"
              class="header-angle"
              :class="{ 'is-active': isDropdownShow }"
          />
        </template>
      </Input>
      <template #content>
        <div class="vk-select__loading" v-if="states.loading"><Icon icon="spinner" spin/></div>
        <div class="vk-select__nodata" v-else-if="filterable && filteredOptions.length === 0">no matching data</div>
        <ul class="vk-select__menu" v-else>
          <template v-for="(item, index) in filteredOptions" :key="index">
            <li
                class="vk-select__menu-item"
                :class="{
              'is-disabled': item.disabled,
              'is-selected': states.selectedOption?.value === item.value ,
              'is-highlighted': states.highlightIndex === index
            }"
                :id="`select-item-${item.value}`"
                @click.stop="itemSelect(item)"
            >
              <RenderVnode :vNode="renderLabel ? renderLabel(item) : item.label"/>
            </li>
          </template>
        </ul>
      </template>
    </VkTooltip>
  </div>
</template>

<style scoped>

</style>