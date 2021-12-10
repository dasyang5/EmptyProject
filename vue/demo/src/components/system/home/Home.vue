<template>
    <el-container>
        <el-main v-if="$common.checkAuth('/api/home/list')">
            Hello
        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: "Home"
    }
</script>
<!--<style>
.el-notification{
    cursor: pointer;
}
</style>-->
