import {describe, expect, test, vi} from "vitest";
import {mount} from "@vue/test-utils";
import VkCollapse from "./Collapse.vue";
import VkCollapseItem from "./CollapseItem.vue";


describe('Collapse.vue', () => {
    test('basic collapse', async () => {
        const onChange = vi.fn()
        const wrapper = mount(() =>
            <VkCollapse modelValue={['a']} onChange={onChange}>
                <VkCollapseItem name="a" title="title a">
                    content a
                </VkCollapseItem>
                <VkCollapseItem name="b" title="title b">
                    content b
                </VkCollapseItem>
                <VkCollapseItem name="c" title="title c" disabled={true}>
                    content c
                </VkCollapseItem>
            </VkCollapse>
            ,
            {
                global: {
                    stubs: ['Icon']
                },
                attachTo: document.body,
            }
        )
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
        expect(onChange).toHaveBeenCalledWith([])
        await secondHeader.trigger('click')
        expect(secondHeader.isVisible()).toBeTruthy()
        expect(onChange).toHaveBeenLastCalledWith(['b'])


        // disabled item
        const disabledHeader = headers[2]
        expect(disabledHeader.classes()).toContain('is-disabled')
        await disabledHeader.trigger('click')
        expect(disabledContent.isVisible()).toBeFalsy()
    })
})