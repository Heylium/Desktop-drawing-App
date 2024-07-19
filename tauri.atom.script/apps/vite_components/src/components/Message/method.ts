import {MessageProps} from "@/components/Message/types.ts";
import {h, render} from "vue";
import MessageConstructor from "./Message.vue"

export const createMessage = (props: MessageProps) => {
  const container = document.createElement('div');
  const vnode = h(MessageConstructor, props);
  render(vnode, container)

  document.body.appendChild(container.firstElementChild!)
}