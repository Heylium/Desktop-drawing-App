import type {ComponentInternalInstance, VNode} from "vue";


export interface MessageProps {
  message?: string | VNode;
  duration?: number;
  showClose?: boolean;
  type?: "success" | "info" | "warning" | "error";
  onDestroy: () => void;
  id: string;
  zIndex: number;
  offset?: number;
}


export interface MessageContext {
  id: string;
  vnode: VNode;
  vm: ComponentInternalInstance,
  props: MessageProps;
  destroy: () => void;
}
export type CreateMessageProps = Omit<MessageProps, 'onDestroy' | 'id' | 'zIndex'>