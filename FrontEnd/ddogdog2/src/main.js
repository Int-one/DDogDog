import { createApp } from 'vue'
import { createPinia } from 'pinia'

import '@flaticon/flaticon-uicons/css/all/all.css';
import App from './App.vue'
import router from './router'

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle';

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
