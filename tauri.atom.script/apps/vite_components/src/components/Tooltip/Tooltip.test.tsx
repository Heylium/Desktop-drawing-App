import {beforeEach, describe, expect, test, vi} from "vitest";
import {mount} from "@vue/test-utils";
import Tooltip from "./Tooltip.vue";


vi.mock('@popperjs/core')
const onVisibleChange = vi.fn()

describe('Tooltip.vue', () => {
  beforeEach(() => {
    vi.useFakeTimers()
  })

  test('basic tooltip', async () => {
    const wrapper = mount(() =>
      <div>
        <div id="outside"></div>
        <Tooltip content="hello tooltip" trigger="click" onVisibleChange={onVisibleChange}>
          <button id="trigger">Trigger</button>
        </Tooltip>
      </div>
      , {
        attachTo: document.body
      }
    )

    // static test
    const triggerArea = wrapper.find('#trigger')
    expect(triggerArea.exists()).toBeTruthy()
    expect(wrapper.find('.vk-tooltip_popper').exists()).toBeFalsy()

    // testing click
    triggerArea.trigger('click')
    await vi.runAllTimers()
    // error
    expect(wrapper.find('.vk-tooltip_popper').exists()).toBeTruthy()
    expect(wrapper.get('.vk-tooltip_popper').text()).toBe('hello tooltip')
    expect('onVisibleChange').toHaveBeenCalledWith(true)
    console.log(wrapper.html())

    wrapper.find('#outside').trigger('click')
    await vi.runAllTimers()
    expect(wrapper.find('.vk-tooltip_popper').exists()).toBeFalsy()
    expect('onVisibleChange').toHaveBeenLastCalledWith(true)


  })
})