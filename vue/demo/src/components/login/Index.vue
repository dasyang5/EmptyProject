<template>
    <div style="height: 100%;width: 100%">
        <transition name="el-zoom-in-center">
            <div v-if="!loginFormVisible" class="index-container">
                <!-- 导航栏 -->
                <index-navigation style="position: fixed;left: 0;top: 0;z-index: 999;width: 100%;background: white;" />
                <!--标题-->
                <index-system-desc></index-system-desc>
                <!-- 系统介绍 -->
                <index-feature></index-feature>
                <!-- 页脚 -->
                <index-footer />
            </div>
            <index-login v-if="loginFormVisible"></index-login>
        </transition>
    </div>
</template>

<script>
    import IndexNavigation from './IndexNavigation'
    import IndexFooter from './IndexFooter'
    import IndexFeature from './IndexFeature'
    import IndexLogin from './IndexLogin'
    import IndexSystemDesc from './IndexSystemDesc'
    export default {
        name: "Login",
        components: {
            IndexNavigation,
            IndexFooter,
            IndexFeature, IndexLogin, IndexSystemDesc
        },
        data() {
            return {
                loginFormVisible: false
            };
        },
        methods: {
            showLogin() {
                this.loginFormVisible = true;
            },
            hideLogin() {
                this.loginFormVisible = false;
            }
        }
    }
</script>

<style>
    #index-container{
    }
</style>
