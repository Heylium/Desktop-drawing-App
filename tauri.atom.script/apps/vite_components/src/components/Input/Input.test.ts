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

  it('支持 v-model', async () => {
    const wrapper = mount(VKInput, {
      props: {
        modelValue: 'test',
        'onUpdate:modelValue': (e: any) => wrapper.setProps({modelValue: e}),
        type: 'text',
      }
    })

    // 初始值
    const input = wrapper.get('input')
    expect(input.element.value).toBe('test')

    // 更新值
    // 注意 setValue 是组合事件会触发 input 以及 change
    await input.setValue('update')
    expect(wrapper.props('modelValue')).toBe('update')
    expect(input.element.value).toBe('update')

    console.log('the events', wrapper.emitted())
    expect(wrapper.emitted()).toHaveProperty('input')
    expect(wrapper.emitted()).toHaveProperty('change')

    // [ [ 'update' ], ...更多事件 ]
    const inputEvent = wrapper.emitted('input')
    const changeEvent = wrapper.emitted('change')
    expect(inputEvent![0]).toEqual(['update'])
    expect(changeEvent![0]).toEqual(['update'])

    // v-model 的异步更新
    await wrapper.setProps({modelValue: 'prop updated'})
    expect(input.element.value).toBe('prop updated')

  });
});