import {describe, expect, test} from "vitest";
import {mount} from "@vue/test-utils";
import VkCollapse from "./Collapse.vue";
import VkCollapseItem from "./CollapseItem.vue";


describe('Collapse.vue', () => {
    test('basic collapse', async () => {
        const wrapper = mount(VkCollapse, {
            props: {
                'modelValue': ['a']
            },
            slots: {
                default:
                    (<>
                        <VkCollapseItem name="a" title="title a">
                            content a
                        </VkCollapseItem>
                        <VkCollapseItem name="b" title="title b">
                            content b
                        </VkCollapseItem>
                        <VkCollapseItem name="c" title="title c" disabled={true}>
                            content c
                        </VkCollapseItem>
                    </>)
            },
                global: {
                    stubs: ['Icon']
                },
                attachTo: document.body,
        })
        const headers = wrapper.findAll('.vk-collapse-item__header')
        const contents = wrapper.findAll('.vk-collapse-item__wrapper')

        // compare count
        expect(headers.length).toBe(3)
        expect(contents.length).toBe(3)

        // compare header text
        const firstHeader = headers[0]
        const secondHeader = headers[1]
        expect(firstHeader.text()).toBe('title a')

        // compare content text
        const firstContent = contents[0]
        const secondContent = contents[1]
        const disabledContent = contents[2]
        expect(firstContent.isVisible()).toBeTruthy()
        expect(secondContent.isVisible()).toBeFalsy()

        // actions
        await firstHeader.trigger('click')
        expect(firstContent.isVisible()).toBeFalsy()
        await secondHeader.trigger('click')
        expect(secondHeader.isVisible()).toBeTruthy()
        expect(wrapper.emitted()).toHaveProperty('change')
        const changeEvent = wrapper.emitted('change') as any[]
        expect(changeEvent).toHaveLength(2)
        expect(changeEvent[0]).toEqual([[]])
        expect(changeEvent[1]).toEqual([['b']])


        // disabled item
        const disabledHeader = headers[2]
        expect(disabledHeader.classes()).toContain('is-disabled')
        await disabledHeader.trigger('click')
        expect(disabledContent.isVisible()).toBeFalsy()
    })
})