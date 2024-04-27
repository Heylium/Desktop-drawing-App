/// <reference types="vitest" />
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import VueMacros from 'unplugin-vue-macros'
import dts from 'vite-plugin-dts'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        VueMacros.vite({
            plugins: {
                vue: vue(),
                vueJsx: vueJsx(),
            },
        }),
        dts({
            tsconfigPath: './tsconfig.build.json'
        })
    ],
    test: {
        globals: true,
        environment: 'jsdom',
    }
})