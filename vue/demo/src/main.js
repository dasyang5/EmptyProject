// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import qs from 'qs'
import VueAxios from "vue-axios";
import App from './App'
import router from './router'
import {Fragment} from 'vue-fragment'
import Cookies from 'js-cookie'
import i18n from './common/lang' // internationalization
import Common from './common/script/common'
import * as echarts from 'echarts';

Vue.config.productionTip = false;
Vue.use(Vuex);
Vue.use(VueAxios, axios);
Vue.use(Fragment.Plugin);
Vue.use(ElementUI, {
    size: Cookies.get('size') || 'medium', // set element-ui default size
    i18n: (key, value) => i18n.t(key, value)
});
Vue.prototype.$echarts = echarts
Vue.prototype.$qs = qs;
Vue.prototype.$common = Common;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  render: h => h(App),
  router,
  components: {App},
  template: '<App/>'
});

//设置国际化
localStorage.setItem('language', i18n.locale);

axios.defaults.baseURL = process.env.BASE_API;
axios.defaults.timeout = 30 * 1000;
axios.interceptors.request.use(
    config => {
        if (localStorage.getItem('Authorization')) {
            config.headers.Authorization = localStorage.getItem('Authorization');
            config.headers['Accept-Language'] = localStorage.getItem('language');
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);
axios.interceptors.response.use(
    response => {
        //下载excel文件的请求，直接放过
        if (response.headers['content-type'].indexOf('application/octet-stream')>=0) {
            return response.data;
        }else if (response && response.data && !response.data.success) {
            ElementUI.Message({
                message: response.data.msg,
                type: 'error'
            });
            throw response.data;
        }
        return response.data;
    },
    error => {
        if (error.response) {
            if (error.response.status === 401) {
                if (router.currentRoute.path!=='/login') {
                    localStorage.setItem('Authorization', null);
                    router.push({path: '/'});
                    ElementUI.Message({
                        message: error.response.status + ': ' + error.response.data.error,
                        type: 'error'
                    });
                }
            } else {
                ElementUI.Message({
                    message: error.response.status + ': ' + error.response.data.error,
                    type: 'error'
                });
            }
        }
        return Promise.reject(error.data.msg)   // 返回接口返回的错误信息
    }
);


