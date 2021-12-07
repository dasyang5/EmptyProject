<template>
    <el-row style="margin-top: 60px; height: 600px" :style="containerStyle">
        <el-col :span="14" :offset="9">
            <div style="font-size: 50px; text-align: center; width: 100%; color: white;padding-top: 470px">
                {{$t('login.title')}}
            </div>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "IndexSystemDesc",
        data() {
            return {
                containerStyle: {
                    backgroundImage: 'url(' + require('@/assets/images/home/desc_bg.jpg') + ')',
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: '100% 100%'
                }
            };
        }
    }
</script>

<style scoped>

</style>
