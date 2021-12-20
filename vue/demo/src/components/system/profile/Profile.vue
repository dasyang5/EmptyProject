<template>
    <el-container id="profile">
        <el-main style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);min-height: 500px">
            <el-divider>基本信息</el-divider>
            <el-row style="margin: 10px">
                <el-col class="key" :span="3" :offset="2">{{$t('login.username')}}</el-col>
                <el-col class="value" :span="17">{{user.username}}</el-col>
            </el-row>
            <el-row style="margin: 10px" v-if="organ!==null">
                <el-col class="key" :span="3" :offset="2">{{$t('system.organ.organName')}}</el-col>
                <el-col class="value" :span="17">{{organ.organName}}</el-col>
            </el-row>
            <el-row style="margin: 10px" v-if="organ!==null">
                <el-col class="key" :span="3" :offset="2">{{$t('system.organ.organId')}}</el-col>
                <el-col class="value" :span="17">{{organ.organId}}</el-col>
            </el-row>
        </el-main>
    </el-container>
</template>

<script>
    export default {
        name: "Profile",
        data() {
            return {
                user: {},
                organ: null
            }
        },
        methods: {
        },
        mounted() {
            this.$nextTick(function () {
                this.axios({
                    method: 'get',
                    url: '/api/user/getProfileData',
                    params: {},
                }).then(res => {
                    this.user = res.user;
                    this.organ = res.organ;
                }).catch(error => {
                    this.loading = false;
                });
            });
        }
    }
</script>

<style lang="scss">
    #profile{
        .key{
        }
        .value{
        }
    }
</style>
