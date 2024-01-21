import {createRouter, createWebHashHistory} from "vue-router";


const routes = [
    {
        path: '/login',
        component: () => import('../views/login.vue')
    },
    {
        path: '/home',
        component: () => import('../views/home.vue')
    }
]

export default createRouter({
    routes,
    history: createWebHashHistory(),
})