<template>
    <el-row id="today">
        <el-col :span="6">
            <el-card class="today-detail">
                <el-row>
                    <el-col :span="10" style="text-align: center">
                        <img class="home-image" src="@/assets/images/home/h1.png" style="height: 80px;">
                    </el-col>
                    <el-col :span="14">
                        <el-row>
                            <el-col :span="24" class="count">
                                {{tasksCount}}
                            </el-col>
                            <el-col :span="24" class="title">
                                {{$t('home.tasksCount')}}
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="today-detail">
                <el-row>
                    <el-col :span="10" style="text-align: center">
                        <img class="home-image" src="@/assets/images/home/h2.png" style="height: 80px;">
                    </el-col>
                    <el-col :span="14">
                        <el-row>
                            <el-col :span="24" class="count">
                                {{taskCount}}
                            </el-col>
                            <el-col :span="24" class="title">
                                {{$t('home.taskCount')}}
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="today-detail">
                <el-row>
                    <el-col :span="10" style="text-align: center">
                        <img class="home-image" src="@/assets/images/home/h3.png" style="height: 80px;">
                    </el-col>
                    <el-col :span="14">
                        <el-row>
                            <el-col :span="24" class="count">
                                {{caseCount}}
                            </el-col>
                            <el-col :span="24" class="title">
                                {{$t('home.caseCount')}}
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="today-detail">
                <el-row>
                    <el-col :span="10" style="text-align: center">
                        <img class="home-image" src="@/assets/images/home/h4.png" style="height: 80px;">
                    </el-col>
                    <el-col :span="14">
                        <el-row>
                            <el-col :span="24" class="count">
                                {{snCount}}
                            </el-col>
                            <el-col :span="24" class="title">
                                {{$t('home.snCount')}}
                            </el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "TodayCount",
        data() {
            return {
                tasksCount: 0,
                taskCount: 0,
                caseCount: 0,
                snCount: 0
            }
        },
        mounted() {
            this.axios({
                method: 'get',
                url: '/api/welcome/getTodayCount'
            }).then(res => {
                this.tasksCount = res.data.tasksCount;
                this.taskCount = res.data.taskCount;
                this.caseCount = res.data.caseCount;
                this.snCount = res.data.snCount;
            }).catch(error => {});
        }
    }
</script>

<style lang="scss">
    #today {
        .today-detail {
            margin: 20px;
            padding: 60px auto;
        }
        .el-divider {
            margin: 10px 0;
        }
        .title {
            font-size: 15px;
            text-align: right;
            font-style: italic;
        }
        .count {
            font-size: 40px; font-weight: bold;text-align: right;color: #2f93ff;
            letter-spacing: 3px;
        }
    }
</style>
