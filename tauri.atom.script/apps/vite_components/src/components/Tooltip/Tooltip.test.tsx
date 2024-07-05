import {describe, expect, test, vi} from "vitest";
import {mount} from "@vue/test-utils";
import Tooltip from "./Tooltip.vue";
import Button from "../Button/Button.vue";


const onVisibleChange = vi.fn()

describe('Tooltip.vue', () => {
  test('basic tooltip', async () => {
    const wrapper = mount(() =>
      <Tooltip content="hello tooltip" trigger="click" onVisibleChange={onVisibleChange}>
        <button id="trigger">Trigger</button>
      </Tooltip>
      , {
        attachTo: document.body
      }
    )

    // static test
    const triggerArea = wrapper.find('#trigger')
    expect(triggerArea.exists()).toBeTruthy()


  })
})