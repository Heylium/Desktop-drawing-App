import {describe, expect, test, vi} from "vitest";
import {testFn} from "./utils";

test('test common matcher', () => {
    const name = 'mk'
    expect(name).toBe('mk')

    expect(2 + 2).toBe(4)
    expect(2 + 2).not.toBe(5)
})

test('test object', () => {
    expect({name: 'mk'}).toEqual({name: 'mk'})
})

describe('functions', () => {
    test('create a mock function', () => {
        const callback = vi.fn()
        testFn(12, callback)
        expect(callback).toHaveBeenCalled()
        expect(callback).toHaveBeenCalledWith(12)
    })

    test('spy on method', () => {
        const obj = {
            getName: () => 1,
        }
        const spy = vi.spyOn(obj, 'getName')
        obj.getName()
        expect(spy).toHaveBeenCalled()
        obj.getName()
        expect(spy).toHaveBeenCalledTimes(2)
    })

    

})