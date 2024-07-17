<script setup lang="ts">
import MyButton from "./components/Button/Button.vue";
import Collapse from "./components/Collapse/Collapse.vue";
import Item from "./components/Collapse/CollapseItem.vue";
import {h, onMounted, ref} from "vue";
import type {ButtonInstance} from "./components/Button/types.ts";
import VkIcon from "./components/Icon/Icon.vue";
import VNode from "./VNode.js";
import {createPopper, Options} from "@popperjs/core";
import type {Instance} from "@popperjs/core";
// import Tooltip from "./components/Tooltip/Tooltip.vue";
import {TooltipInstance} from "@/components/Tooltip/types.ts";
import DropDown from "@/components/DropDown/Dropdown.tsx";
import {MenuOption} from "@/components/DropDown/types.ts";

const buttonRef = ref<ButtonInstance | null>(null)
const tooltipRef = ref<TooltipInstance | null>(null)

const overlayNode = ref<HTMLElement>()
const triggerNode = ref<HTMLElement>()
let popperInstance: Instance | null = null
let size = ref<any>('3x')

const trigger = ref<any>('click')
// const options: Partial<Options> = {placement: 'right-end', strategy: 'fixed'}
const options: MenuOption[] = [
  {key: 1, label: h('b', 'this is bold'),},
  {key: 2, label: 'item2', disabled: true},
  {key: 3, label: 'item3', divided: true},
  {key: 4, label: 'item4',},
]

const inlineConsole = (...args: any[]) => {
  console.log(...args)
}

const open = () => {
  tooltipRef.value?.show()
}
const close = () => {
  tooltipRef.value?.hide()
}

onMounted(() => {
  console.log(`buttonRef:`, buttonRef.value?.ref)

  if (overlayNode.value && triggerNode.value) {
    popperInstance = createPopper(triggerNode.value, overlayNode.value, {placement: 'right'})
  }

  setTimeout(() => {
    openedValue.value = ['a', 'b']
    size.value = '2xl'
    // trigger.value = 'hover'
    // popperInstance?.setOptions({
    //   placement: 'bottom',
    // })
  }, 2000)
})


const openedValue = ref(['a'])
</script>

<template>
  <div>
<!--    <a href="https://vitejs.dev" target="_blank">-->
      <DropDown
          content="hello world"
          placement="bottom"
          :trigger="trigger"
          :open-delay="1000"
          :close-delay="1000"
          :menu-options="options"
          @visible-change="e => inlineConsole('visible-change', e)"
          @select="e => inlineConsole('select', e)"
          manual
          ref="tooltipRef"
      >
        <img src="/vite.svg" class="logo" alt="Vite logo"/>
      </DropDown>
<!--    </a>-->
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>

  <VNode msg="hello" ></VNode>

  <VkIcon icon="arrow-up" size="2xl" type="danger"></VkIcon>

  <main>
    <my-button @click="open" type="primary" plain ref="buttonRef">Defined Button</my-button>
    <my-button @click="close" plain >Plain</my-button>
    <my-button round >Round</my-button>
    <my-button circle >Circle</my-button>
    <my-button disabled >Disabled</my-button>
    <br/>
    <br/>
    <my-button size="large" loading >Loading</my-button>
    <my-button size="large" icon="arrow-up" >Icon</my-button>


    <Collapse v-model="openedValue" accordion>
      <Item name="a">
        <template #title>
          <h1>nice title</h1>
        </template>
        <h1>headline title</h1>
        <div> this is content area </div>
      </Item>
      <Item name="b" title="nice title b item b">
        <div>this is area b</div>
      </Item>
      <Item name="c" title="nice title c " disabled>
        <div> this is area c </div>
      </Item>
    </Collapse>
    {{openedValue}}
  </main>

  <a href="#">The link</a>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
