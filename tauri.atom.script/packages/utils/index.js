import dayjs from 'dayjs'
export function format(time, f = 'YYYY-MM-DD') {
    return dayjs(time).format(f)
}