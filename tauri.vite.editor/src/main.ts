import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { invoke } from "@tauri-apps/api";

createApp(App).mount('#app')

invoke('greet', { name: 'world'})
    .then(response => console.log(response))
