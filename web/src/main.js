import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import store from './store'
import './utils/flexible'
import './assets/init.css'
import './utils/element'
import store from './utils/store'
import moment from 'moment';
import VMdEditor from '@kangc/v-md-editor';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import 'prismjs';
import 'prismjs/components/prism-json';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import '@kangc/v-md-editor/lib/theme/style/github.css';

VMdEditor.use(githubTheme, {
  Prism,
});

Vue.use(VMdEditor);
Vue.prototype.$store= store
Vue.prototype.$moment= moment
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
