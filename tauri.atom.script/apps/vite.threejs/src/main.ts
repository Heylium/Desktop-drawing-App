import { createApp } from 'vue'
import './style.css'
import routers from "./routers";
import App from './App.vue'

// createApp(App).mount('#app')
const app = createApp(App);
app
    .use(routers)
    .mount('#app')

