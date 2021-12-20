<template>
    <fragment v-if="item.hasAuth&&item.type!=='API'">
        <el-menu-item v-if="item.type === 'MENU'" :key="item.id" :index="item.path">{{$i18n.locale.startsWith('zh')?item.name:item.nameEn}}</el-menu-item>
        <el-submenu v-else :index="item.path" :key="item.id">
            <template slot="title">{{$i18n.locale.startsWith('zh')?item.name:item.nameEn}}</template>
            <menu-item v-for="child in item.child" :key="child.id" :item="child">{{$i18n.locale.startsWith('zh')?child.name:child.nameEn}}</menu-item>
        </el-submenu>
    </fragment>
</template>

<script>
    export default {
        name: "MenuItem",
        props: {
            item: {
                type: Object,
                require: true
            }
        }
    }
</script>

<style scoped>

</style>
