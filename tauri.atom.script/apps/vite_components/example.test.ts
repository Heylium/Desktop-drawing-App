import {describe, expect, test, vi, Mocked} from "vitest";
import {testFn, request} from "./utils";
import axios from "axios";


vi.mock('axios')
const mockedAxios = axios as Mocked<typeof axios>

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

    test('mock third party module', async () => {
        mockedAxios.get.mockImplementation(() => Promise.resolve({data: 123}))
        const result = await request()
        expect(result).toBe(123)
    })
})