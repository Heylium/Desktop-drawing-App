import {describe, expect, test} from "vitest";
import {closeAll, createMessage} from "./method.ts";
import {nextTick} from "vue";

export const rAF = async () => {
  return new Promise((res) => {
    requestAnimationFrame(() => {
      requestAnimationFrame(async () => {
        res(null)
        await nextTick()
      })
    })
  })
}

function getTopValue(element: Element) {
  const styles = window.getComputedStyle(element)
  const topValue = styles.getPropertyValue('top')
  return Number.parseFloat(topValue)
}

describe('createMessage', () => {

  test('调用方法应该创建对应的 Message 组件', async () => {
    const instance = createMessage({message: "Hello World!", duration: 0});
    await rAF()
    expect(document.querySelector('.vk-message')).toBeTruthy()
    instance.destroy()
    await rAF()
    expect(document.querySelector('.vk-message')).toBeFalsy()
  })

  test('多次调用方法应该创建多个实例', async () => {
    createMessage({message: "Hello World 1", duration: 0});
    createMessage({message: "Hello World 2", duration: 0});

    await rAF()
    const elements = document.querySelectorAll('.vk-message')
    expect(elements.length).toBe(2)
    closeAll()
    await rAF()
    expect(document.querySelector('.vk-message')).toBeFalsy()
  })

  test('创建多个实例应该设置正确的 offset', async () => {
    createMessage({message: "Hello World 1", duration: 0, offset: 100});
    createMessage({message: "Hello World 2", duration: 0, offset: 50});

    await rAF()
    const elements = document.querySelectorAll('.vk-message')
    expect(elements.length).toBe(2)
    const firstElementTop = getTopValue(elements[0])
    const secondElementTop = getTopValue(elements[1])

    /**
     * in jsdom, element will always be 0
     * @link https://github.com/jsdom/jsdom/issues/1590
     */
    expect(firstElementTop).toBe(100)
    expect(secondElementTop).toBe(100 + 50)
  })

});