import {h, render} from "vue";
import type {CreateMessageProps, MessageContext} from "@/components/Message/types.ts";
import MessageConstructor from "./Message.vue"

let seed = 1
const instances: MessageContext[] = []
export const createMessage = (props: CreateMessageProps) => {
  const id= `message_${seed++}`
  const container = document.createElement('div');
  const destroy = () => {
    // delete instance in array
    const idx = instances.findIndex(instance => instance.id === id)
    if (idx === -1) return;
    instances.splice(idx, 1);
    render(null, container);
  }
  const newProps = {
    ...props,
    onDestroy: destroy,
  }
  const vnode = h(MessageConstructor, newProps);
  render(vnode, container)

  document.body.appendChild(container.firstElementChild!)
  const instance = {
    id,
    vnode,
    props: newProps
  }
  instances.push(instance)
  return instance
}

export const getLastInstance = () => {
  return instances.at(-1)
}