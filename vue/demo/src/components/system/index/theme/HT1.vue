<template>
    <el-container id="index-container">
        <el-header>
            <el-menu :default-active="activeIndex" class="el-menu-index" mode="horizontal" @select="handleSelect">
                <menu-item  v-for="item in menuList" :key="item.id" :item="item"/>
                <el-submenu index="/user" style="float: right">
                    <template slot="title">{{$t('system.index.welcome')}}: {{username}}</template>
                    <el-menu-item index="/profile">{{$t('system.index.profile')}}</el-menu-item>
                    <el-menu-item index="/changePassword">{{$t('system.index.changePassword')}}</el-menu-item>
                    <el-menu-item index="/theme">{{$t('system.index.changeTheme')}}</el-menu-item>
                    <el-menu-item index="logout">{{$t('system.index.logout')}}</el-menu-item>
                </el-submenu>
                <el-submenu index="lang" style="float: right">
                    <template slot="title">{{$t('system.index.language')}}</template>
                    <el-menu-item index="zh">中文</el-menu-item>
                    <el-menu-item index="en">English</el-menu-item>
                </el-submenu>
            </el-menu>
        </el-header>
        <el-main>
            <router-view/>
        </el-main>
        <change-password ref="changePassword"/>
    </el-container>
</template>

<script>
    import Cookies from 'js-cookie'
    import MenuItem from '../components/MenuItem'
    import ChangePassword from '../components/ChangePassword'
    export default {
        components: {MenuItem, ChangePassword},
        data() {
            return {
                username: localStorage.username,
                role: localStorage.role,
                activeIndex: 'home',
                currentIndex: '',
                menuList: [],
                loading: true
            };
        },
        methods: {
            logout() {
                this.axios({
                    method: 'get',
                    url: '/logout',
                    data: ""
                }).then(res => {
                    localStorage.setItem('Authorization', null);
                    this.$router.push({path: '/login'})
                }).catch(error => {});
            },
            handleSelect(key, keyPath) {
                if (key === 'logout') {
                    this.logout();
                }else if (keyPath[0] === 'lang') {
                    this.$i18n.locale = key;
                    Cookies.set('language', key);
                    localStorage.setItem('language', key);
                }else if(key === '/changePassword'){
                    this.$refs.changePassword.show();
                }else if(key === '/theme'){
                    this.axios({
                        method: 'post',
                        url: '/api/user/updateTheme',
                        params: {theme: 'vt1'}
                    }).then(res => {
                        localStorage.setItem('theme', 'vt1');
                        location.reload();
                    }).catch(error => {});
                } else {
                    let path = '';
                    for (let i = 0; i < keyPath.length; i++) {
                        path += keyPath[i];
                    }
                    if (path === this.$router.currentRoute.path) {
                        return;
                    }
                    this.currentIndex = path;
                    this.$router.push({path: path});
                }
            }
        },
        mounted() {
            this.$nextTick(function () {
                // this.listMenu();
                this.menuList = JSON.parse(localStorage.getItem('auth'));
            });
        }
    }
</script>

<style type="text/css" lang="scss">
    #index-container{
        height: 100%;
        width: 100%;
        .el-menu-index{
            width: 100%;
        }
    }
</style>
