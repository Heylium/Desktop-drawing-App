import {describe, expect, it} from "vitest";
import {mount} from "@vue/test-utils";
import VKInput from "./Input.vue";


describe('Input', () => {

  it('基本展示', () => {
    const wrapper = mount(VKInput, {
      props: {
        size: 'small',
        type: 'text',
      },
      slots: {
        prepend: 'prepend',
        prefix: 'prefix',
      }
    });
    console.log(wrapper.html())
    expect(wrapper.classes()).toContain('vk-input--small')
    expect(wrapper.classes()).toContain('is-prepend')
  })

});