<template>
    <el-container>
        <el-main v-if="$common.checkAuth('/api/home/list')">

        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {

            }
        },
        methods: {

        }
    }
</script>
<!--<style>
.el-notification{
    cursor: pointer;
}
</style>-->
