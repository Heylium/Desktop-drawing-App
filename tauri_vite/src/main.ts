import { createApp } from 'vue';
import './style.css';
// @ts-ignore
import App from './App.vue';

import {createRouter, createWebHashHistory} from "vue-router";

// @ts-ignore
import HelloWorld from "./components/HelloWorld.vue";

// @ts-ignore
import Editor from "./components/Editor.vue";

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
    {path: '/', component: HelloWorld},
    {path: '/editor', component: Editor},
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单


createApp(App)
    .use(router)
    .mount('#app')

