<template>
    <div style="height: 100%;width: 100%">
        <div class="index-container">
            <!-- 导航栏 -->
            <index-navigation style="position: fixed;left: 0;top: 0;z-index: 999;width: 100%;background: white;" />
            <!--标题-->
            <index-system-desc></index-system-desc>
            <!-- 系统介绍 -->
            <index-feature></index-feature>
            <!-- 页脚 -->
            <index-footer />
        </div>
        <index-login ref="login"></index-login>
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
        methods: {
            showLogin() {
                this.$refs.login.show();
            }
        }
    }
</script>

<style>
    #index-container{
    }
</style>
