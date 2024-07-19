import type {CreateMessageProps} from "@/components/Message/types.ts";
import {h, render} from "vue";
import MessageConstructor from "./Message.vue"

export const createMessage = (props: CreateMessageProps) => {
  const container = document.createElement('div');
  const destroy = () => {
    render(null, container);
  }
  const newProps = {
    ...props,
    onDestroy: destroy,
  }
  const vnode = h(MessageConstructor, newProps);
  render(vnode, container)

  document.body.appendChild(container.firstElementChild!)
}