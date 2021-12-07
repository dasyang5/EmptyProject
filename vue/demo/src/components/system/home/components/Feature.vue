<template>
    <el-row id="feature">
        <el-col :span="6">
            <el-card class="feature-detail">
                <el-row @click.native="$router.push({path: '/test/case'})">
                    <el-col :span="10">
                        <img class="home-image" src="@/assets/images/home/f1.png" style="height: 60px; margin-top: 10px">
                    </el-col>
                    <el-col :span="14" style="line-height: 80px;font-size: 20px">
                        {{$t('feature.case')}}
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="feature-detail">
                <el-row @click.native="$router.push({path: '/test/app'})">
                    <el-col :span="10">
                        <img class="home-image" src="@/assets/images/home/f2.png" style="height: 60px; margin-top: 10px">
                    </el-col>
                    <el-col :span="14" style="line-height: 80px;font-size: 20px">
                        {{$t('feature.application')}}
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="feature-detail">
                <el-row @click.native="$router.push({path: '/terminal/task'})">
                    <el-col :span="10">
                        <img class="home-image" src="@/assets/images/home/f3.png" style="height: 60px; margin-top: 10px">
                    </el-col>
                    <el-col :span="14" style="line-height: 80px;font-size: 20px">
                        {{$t('feature.task')}}
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="feature-detail">
                <el-row @click.native="$router.push({path: '/terminal/tasks'})">
                    <el-col :span="10">
                        <img class="home-image" src="@/assets/images/home/f4.png" style="height: 60px; margin-top: 10px">
                    </el-col>
                    <el-col :span="14" style="line-height: 80px;font-size: 20px">
                        {{$t('feature.tasks')}}
                    </el-col>
                </el-row>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "Feature"
    }
</script>

<style lang="scss">
    #feature {
        .feature-detail {
            margin: 20px;
            padding: 60px auto;
            text-align: center;
            cursor: pointer;
        }
        .feature-detail:hover {
            box-shadow: 0 2px 4px rgba(0, 133, 255, 0.29), 0 0 6px rgba(0, 133, 255, 0.09)
        }
    }
</style>
