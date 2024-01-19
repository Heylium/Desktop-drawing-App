

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