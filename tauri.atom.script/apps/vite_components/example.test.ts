import { expect, test} from "vitest";


test('test common matcher', () => {
    const name = 'mk'
    expect(name).toBe('mk')

    expect(2 + 2).toBe(4)
    expect(2 + 2).not.toBe(5)
})

test('test object', () => {
    expect({name: 'mk'}).toEqual({name: 'mk'})
})