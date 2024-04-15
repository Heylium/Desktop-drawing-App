import { createApp } from 'vue'
import {library} from "@fortawesome/fontawesome-svg-core";
import {faUserSecret} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import App from './App.vue'

library.add(faUserSecret)
// import './style.css'
import './styles/index.css'



createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon)
    .mount('#app')
