import {beforeAll, describe, expect, test, vi} from "vitest";
import type {DOMWrapper, VueWrapper} from "@vue/test-utils";
import {mount} from "@vue/test-utils";
import VkCollapse from "./Collapse.vue";
import VkCollapseItem from "./CollapseItem.vue";


const onChange = vi.fn()
let wrapper: VueWrapper
let headers: DOMWrapper<Element>[],
    contents: DOMWrapper<Element>[]
let firstContent: DOMWrapper<Element>,
    secondContent: DOMWrapper<Element>,
    disabledContent: DOMWrapper<Element>,
    firstHeader: DOMWrapper<Element>,
    secondHeader: DOMWrapper<Element>,
    disabledHeader: DOMWrapper<Element>

describe('Collapse.vue', () => {
    beforeAll(() => {
        wrapper = mount(() =>
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

        headers = wrapper.findAll('.vk-collapse-item__header')
        contents = wrapper.findAll('.vk-collapse-item__wrapper')
        firstHeader = headers[0]
        secondHeader = headers[1]
        firstContent = contents[0]
        secondContent = contents[1]
        disabledContent = contents[2]
        disabledHeader = headers[2]
    })

    test('测试基础结构以及对应文本', () => {
        // compare count
        expect(headers.length).toBe(3)
        expect(contents.length).toBe(3)

        // compare header text
        expect(firstHeader.text()).toBe('title a')

        // compare content text
        expect(firstContent.isVisible()).toBeTruthy()
        expect(secondContent.isVisible()).toBeFalsy()
        expect(firstContent.text()).toBe('content a')

    })
    test('点击标题展开/关闭内容', async () => {
        // actions
        await firstHeader.trigger('click')
        expect(firstContent.isVisible()).toBeFalsy()
        await secondHeader.trigger('click')
        expect(secondHeader.isVisible()).toBeTruthy()
    })
    test('发送正确的事件', () => {
        expect(onChange).toHaveBeenCalledTimes(2)
        expect(onChange).toHaveBeenCalledWith([])
        expect(onChange).toHaveBeenLastCalledWith(['b'])

    })
    test('disabled 内容应该没有反应', async () => {
        // 清空之前测试的方法
        onChange.mockClear()
        // disabled item
        expect(disabledHeader.classes()).toContain('is-disabled')
        await disabledHeader.trigger('click')
        expect(disabledContent.isVisible()).toBeFalsy()

        expect(onChange).not.toHaveBeenCalled()
    })
})