import { describe, test, expect } from "vitest";
import { mount } from "@vue/test-utils";
import Button from "./Button.vue";


describe('Button.vue', () => {
    test('basic button', () => {
        const wrapper = mount(Button, {
            props: {
                type: 'primary',
            },
            slots: {
                default: 'button',
            }
        })
        console.log(wrapper.html())
        expect(wrapper.classes()).toContain('vk-button--primary')

        // test slot
        expect(wrapper.get('button').text()).toBe('button')

        // events
        wrapper.get('button').trigger('click')
        console.log(wrapper.emitted())
        expect(wrapper.emitted()).toHaveProperty('click')
    })

    test('disabled', () => {
        const wrapper = mount(Button, {
            props: {
                disabled: true,
            },
            slots: {
                default: 'disabled',
            }
        })

        // button attribute
        expect(wrapper.attributes('disabled')).toBeDefined()

        // button html node
        expect(wrapper.find('button').element.disabled).toBeDefined()

        // click disabled button
        wrapper.get('button').trigger('click')
        console.log(wrapper.emitted())
        expect(wrapper.emitted()).not.toHaveProperty('click')
    })
})