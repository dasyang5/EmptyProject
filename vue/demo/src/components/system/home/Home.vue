<template>
    <el-container>
        <el-main v-if="$common.checkAuth('/api/home/list')">
            {{msg}}
        </el-main>
    </el-container>
</template>

<script>
    import Feature from './components/Feature'
    import HomeTitle from './components/HomeTitle'
    import Monitor from './components/Monitor'
    import MsgDetail from "./components/MsgDetail"
    import TodayCount from './components/TodayCount'
    export default {
        name: "Home",
        data() {
            return {
                msg: 'Welcome to demo Project'
            }
        }
    }
</script>
<!--<style>
.el-notification{
    cursor: pointer;
}
</style>-->
