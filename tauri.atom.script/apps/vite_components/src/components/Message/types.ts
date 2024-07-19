import type {VNode} from "vue";


export interface MessageProps {
  message?: string | VNode;
  duration?: number;
  showClose?: boolean;
  type?: "success" | "info" | "warning" | "error";
  onDestroy: () => void;
}

export type CreateMessageProps = Omit<MessageProps, 'onDestroy'>