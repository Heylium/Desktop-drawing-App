import type {VNode} from "vue";
import type {TooltipProps} from "@/components/Tooltip/types.ts";


export interface DropDownProps extends TooltipProps {
  menuOptions: MenuOption[];
}

export interface MenuOption {
  label: string | VNode;
  key: string | null;
  disabled?: boolean;
  divided?: boolean;
}

export interface DropDownEmits {
  (e: 'visible-change', value: boolean): void;
  (e: 'select', value: MenuOption): void;
}

export interface DropDownInstance {
  show: () => void;
  hide: () => void;
}