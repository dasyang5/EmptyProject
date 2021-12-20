<template>
<!--    <ht1></ht1>-->
    <ht1 v-if="theme === 'ht1'"/>
    <vt1 v-else/>
</template>

<script>
    import ht1 from './theme/HT1'
    import vt1 from './theme/VT1'
    export default {
        name: "Index",
        components: {ht1, vt1},
        data() {
            return {
                theme: localStorage.theme
            }
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
