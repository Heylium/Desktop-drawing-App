import {describe, test} from "vitest";
import {mount} from "@vue/test-utils";
import {h} from "vue";
import VkCollapse from "./Collapse.vue";
import VkCollapseItem from "./CollapseItem.vue";


describe('Collapse.vue', () => {
    test('basic collapse', () => {
        const wrapper = mount(VkCollapse, {
            props: {
                modelValue: ['a']
            },
            slots: {
                default: h(VkCollapseItem, { name: 'a', title: 'Title A'}, 'content a')
            },
            global: {
                stubs: ['Icon']
            }
        })
        console.log(wrapper.html())
    })
})