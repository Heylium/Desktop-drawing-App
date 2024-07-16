import {computed, defineComponent, Fragment, PropType} from "vue";
import type {Options, Placement} from "@popperjs/core";
import {MenuOption} from "@/components/DropDown/types.ts";
import VkTooltip from "@/components/Tooltip/Tooltip.vue";
import RenderVNode from "@/components/Common/RenderVNode.ts";


export default defineComponent({
  name: 'VKDropdown',
  props: {
    placement: {
      type: String as PropType<Placement>,
      default: 'bottom',
    },
    trigger: {
      type: String as PropType<'hover' | 'click'>,
      default: 'hover',
    },
    transition: {
      type: String,
      default: 'fade',
    },
    openDelay: {
      type: Number,
      default: 0,
    },
    closeDelay: {
      type: Number,
      default: 0,
    },
    popperOptions: {
      type: Object as PropType<Options>,
    },
    menuOptions: {
      type: Array as PropType<MenuOption[]>,
      required: false,
    },
    closeAfterClick: {
      type: Boolean,
      default: true,
    }
  },
  setup(props, { emit, slots }) {

    const options = computed(() => {
      return props.menuOptions?.map(item => {
        return (
          <Fragment key={item.key}>
            {item.divided ? <li role="separator" class="divided-placeholder" /> : ''}
            <li
              class="vk-dropdown__item"
              id={`dropdown-item-${item.key}`}
            >
              {item.label}
            </li>
          </Fragment>
        )
      })
    })

    return () => (
      <div
        class="vk-dropdown"
      >
        <VkTooltip
        trigger={props.trigger}
        placement={props.placement}
        popper-options={props.popperOptions}
        open-delay={props.openDelay}
        close-delay={props.closeDelay}
        >{{
          default: () => slots.default && slots.default(),
          content: () => (
            <ul class="vk-dropdown__menu">
              {options.value}
            </ul>
          )
        }}</VkTooltip>
      </div>
    )
  }
})