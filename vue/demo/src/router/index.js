import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login/Index'
import Index from '@/components/system/index/Index'
import ListUser from '@/components/system/user/ListUser'
import ListMenuResource from '@/components/system/menuResource/ListMenuResource'
import ListRole from '@/components/system/role/ListRole'
import ListOrgan from '@/components/system/organ/ListOrgan'
import Home from '@/components/system/home/Home'
import Profile from '@/components/system/profile/Profile'

Vue.use(Router);

const router = new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            name: 'login',
            component: login
        },
        {
            path: '/system',
            name: 'system',
            component: Index,
            children: [
                {
                    path: 'home',
                    name: 'systemHome',
                    component: Home
                }, {
                    path: 'user',
                    name: 'listUser',
                    component: ListUser
                }, {
                    path: 'menuResource',
                    name: 'listMenuResource',
                    component: ListMenuResource
                }, {
                    path: 'role',
                    name: 'listRole',
                    component: ListRole
                }, {
                    path: 'organ',
                    name: 'listOrgan',
                    component: ListOrgan
                }
            ]
        },
        {
            path: '/user',
            name: 'user',
            component: Index,
            children: [
                {
                    path: 'profile',
                    name: 'profile',
                    component: Profile
                }
            ]
        }
    ]
});

router.beforeEach((to, from, next) => {
    let token = localStorage.getItem('Authorization');

    if (token === 'null' || token === '' || token === null) {

        if (to.path === '/login') {
            next();
        } else {
            next('/login');
        }

        return;
    }

    if (to.path === '/login') {
        next('/system/home');
    } else {
        next();
    }
});


export default router;
