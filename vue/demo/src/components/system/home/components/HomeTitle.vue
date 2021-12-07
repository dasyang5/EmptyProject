<template>
    <el-row>
        <el-col :span="24" style="margin: 0 20px">
            {{text}}
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "Title",
        props: {
            text: {
                type: String,
                require: true
            }
        }
    }
</script>

<style scoped>

</style>
