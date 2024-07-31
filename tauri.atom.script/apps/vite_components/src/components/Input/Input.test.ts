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
    // should render input
    expect(wrapper.find('input').exists()).toBeTruthy()
    expect(wrapper.get('input').attributes('type')).toBe('text')
    // slots
    expect(wrapper.find('.vk-input__prepend').exists()).toBeTruthy()
    expect(wrapper.get('.vk-input__prepend').text()).toBe('prepend')
    expect(wrapper.find('.vk-input__prefix').exists()).toBeTruthy()
    expect(wrapper.get('.vk-input__prefix').text()).toBe('prefix')

    // textarea
    const wrapper2 = mount(VKInput, {
      props: {
        type: 'textarea',
        modelValue: ''
      }
    })
    expect(wrapper2.find('textarea').exists()).toBeTruthy()
  })

});