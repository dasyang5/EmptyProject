<template>
    <el-container id="indexContainer" style="height: 100%">
        <el-header style="height: auto;border-bottom: 2px solid whitesmoke" id="indexHeader">
            <el-container id="headerContainer">
                <el-aside style="text-align: center;border-right: 2px solid whitesmoke" width="270px">
                    <img style="height: 50px" src="@/assets/images/logo.png">
                </el-aside>
                <el-main style="text-align: right">
                    <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left; margin-top: 20px;margin-left: 20px;">
                        <el-breadcrumb-item v-for="(item, index) in breadcrumbData" :key="index">{{$i18n.locale.startsWith('zh')?item.name:item.nameEn}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <el-dropdown  @command="changeLang" style="margin-right: 20px;line-height: 30px;position: absolute;right: 45px;top: 12px;">
                          <span class="el-dropdown-link">
<!--                            {{$t('system.index.language')}}-->
                              {{$i18n.locale.startsWith('zh')?'中文':'English'}}
                          </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="zh">中文</el-dropdown-item>
                            <el-dropdown-item command="en">English</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-popover
                        style="margin-right: 20px"
                        placement="bottom"
                        width="300"
                        trigger="hover">
                        <div style="padding-left: 10px">
                            {{$t('system.user.username')}}: <label style="color: #409EFF">{{username}}</label>
                        </div>
                        <div style="height: 15px; width: 100%;border-bottom: 2px solid rgba(212,212,212,0.34);margin-bottom: 10px;"></div>
                        <div class="drop-down-menu-item" @click="handleMenu('/user/profile')"><i class="el-icon-postcard" style="margin-right: 10px"></i>{{$t('system.index.profile')}}</div>
                        <div class="drop-down-menu-item" @click="handleMenu('/changePassword')"><i class="el-icon-edit-outline" style="margin-right: 10px"></i>{{$t('system.index.changePassword')}}</div>
                        <div class="drop-down-menu-item" @click="changeTheme('ht1')"><i class="el-icon-s-operation" style="margin-right: 10px"></i>{{$t('system.index.changeTheme')}}</div>
                        <div style="height: 15px; width: 100%;border-bottom: 2px solid rgba(212,212,212,0.34);margin-bottom: 10px;"></div>
                        <div class="drop-down-menu-item" style="text-align: center;border: 1px solid rgba(188,188,188,0.66);margin-top: 10px" @click="handleMenu('logout')">{{$t('system.index.logout')}}</div>
                        <el-button slot="reference" type="text" plain>
                            <img style="height: 30px;" src="@/assets/images/system/index/user.png">
                        </el-button>
                    </el-popover>
                </el-main>
            </el-container>
        </el-header>
        <el-main id="indexMain">
            <el-container id="mainContainer">
                <el-aside width="270px" style="background-color: #325164">
                    <el-menu :default-active="activeIndex" class="el-menu-index"
                             active-text-color="#ffd04b"
                             text-color="white"
                             background-color="#325164"
                             @select="handleSelect">
                        <menu-item  v-for="item in menuList" :key="item.id" :item="item"/>
                    </el-menu>
                </el-aside>
                <el-container>
                    <el-main style="padding: 20px">
                        <router-view/>
                    </el-main>
                    <el-footer style="border-top: 1px solid whitesmoke;height: auto;text-align: center;padding: 10px;color: grey">
                        © 2020 Newland Payment Technology. All rights reserved.
                    </el-footer>
                </el-container>
            </el-container>
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
                loading: true,
                breadcrumbData: []
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
                let path = '';
                for (let i = 0; i < keyPath.length; i++) {
                    path += keyPath[i];
                }
                if (path === this.$router.currentRoute.path) {

                    return;
                }

                //面包屑导航
                this.breadcrumbData = [];
                let loop = this.menuList;
                for (let i = 0; i < keyPath.length; i++) {
                    loop.forEach(b => {
                        if (keyPath[i] === b.path) {
                            this.breadcrumbData[i] = {
                                name: b.name,
                                nameEn: b.nameEn,
                                path: b.path
                            };
                            loop = b.child
                        }
                    });
                }

                this.currentIndex = path;
                this.$router.push({path: path});
            },
            changeLang(lang) {
                this.$i18n.locale = lang;
                Cookies.set('language', lang);
                localStorage.setItem('language', lang);
            },
            changeTheme(theme) {
                this.axios({
                    method: 'post',
                    url: '/api/user/updateTheme',
                    params: {theme: theme}
                }).then(res => {
                    localStorage.setItem('theme', theme);
                    location.reload();
                }).catch(error => {});
            },
            handleMenu(key) {
                if (key === 'logout') {
                    this.logout();
                }else if (key === '/changePassword') {
                    this.$refs.changePassword.show();
                } else {
                    if (key === this.$router.currentRoute.path) {
                        return;
                    }

                    this.$router.push({path: key});
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
    #indexContainer, #headerContainer, #mainContainer{
        height: 100%;
        width: 100%;
        .el-menu-index{
            width: 100%;
        }

        .el-main, .el-header, .el-container{
            padding: 0;
            margin: 0;
        }
        .el-menu{
            border: none;
        }
        .el-dropdown-link {
            cursor: pointer;
            color: #409EFF;
        }
        .el-icon-arrow-down {
            font-size: 12px;
        }
    }

    .drop-down-menu-item{
        cursor: pointer;
        padding: 5px 10px;
        border-radius: 3px;
    }
    .drop-down-menu-item:hover {
        background-color: rgba(168, 168, 168, 0.47);
    }

    #indexHeader, #indexMain{
        padding: 0;
        margin: 0;
    }
</style>
