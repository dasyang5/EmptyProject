<template>
    <div>
        <el-row>
            <el-col :span="16">
                <el-card style="margin: 20px">
                    <div slot="header">
                        <span>{{ this.$t("home.transactionStatistics") }}</span>
                    </div>
                    <div style="height: 220px; width: 100%" id="chart-1"></div>
                </el-card>
            </el-col>
           <!-- <el-col :span="8">
                <el-card style="margin: 20px">
                    <div slot="header">
                        <span>{{this.$t('home.progress')}}</span>
                    </div>
                    <div style="height: 250px; width: 100%" id="chart-2"></div>
                </el-card>
            </el-col>-->
            <el-col :span="8">
                <el-card style="margin: 20px">
                    <div slot="header">
                        <span>{{ this.$t('home.historicalNews') }}</span>
                    </div>
                    <el-empty v-if="tableData.length<=0" style="height: 220px"></el-empty>
                    <el-row v-for="(obj, index) in tableData" :key="index">
                        <el-col :span="12">{{obj.title}}</el-col>
                        <el-col :span="6">11-30</el-col>
                        <el-col :span="6">超时</el-col>
                    </el-row>
                  <!--  <el-table
                        border
                        v-loading="loading"
                        lazy
                        :data="tableData"
                        height="250px"
                        style="width: 100%">
                        <el-table-column
                            prop="title"
                            :label="$t('home.msgTitle')"
                            align="center">
                            <template slot-scope="scope">
                                <el-button @click="openMsg(scope.row)" type="text" size="mini">
                                    {{ scope.row.title }}
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            align="center"
                            :label="$t('home.msgType')"
                            width="100">
                            <template slot-scope="scope">
                                <fragment>
                                    <label
                                        v-if="scope.row.msgType === 'TASK-FINISH'">{{ $t('status.taskFinish') }}</label>
                                    <label
                                        v-else-if="scope.row.msgType === 'CASE-FINISH'">{{
                                            $t('status.caseFinish')
                                        }}</label>
                                    <label
                                        v-else-if="scope.row.msgType === 'EXEC-EXCEPTION'">{{
                                            $t('status.execException')
                                        }}</label>
                                    <label
                                        v-else-if="scope.row.msgType === 'TIMEOUT-EXCEPTION'">{{
                                            $t('status.timeoutException')
                                        }}</label>
                                    <label
                                        v-else-if="scope.row.msgType === 'INTERVAL-EXCEPTION'">{{
                                            $t('status.intervalException')
                                        }}</label>
                                </fragment>
                            </template>
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            align="center"
                            :label="$t('home.status')">
                            <template slot-scope="scope">
                                <fragment>
                                    <label v-if="scope.row.isRead === 'Y'">{{ $t('status.isRead') }}</label>
                                    <label v-else-if="scope.row.isRead === 'N'">{{ $t('status.notRead') }}</label>
                                </fragment>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="creTime"
                            :label="$t('common.createTime')"
                            align="center">
                        </el-table-column>

                    </el-table>-->
                </el-card>
            </el-col>
            <!--<el-col :span="8">
                <el-card style="margin: 20px">
                    <div slot="header">
                        <span>{{ this.$t('home.onlineSn') }}</span>
                    </div>
                    <div style="height: 250px; width: 100%" id="chart-4"></div>
                </el-card>
            </el-col>-->
        </el-row>
        <show-msg ref="showMsg"></show-msg>
    </div>
</template>


<script>
import * as echarts from 'echarts';
import ShowMsg from "./showMsg";

export default {
    name: "Monitor",
    components: {ShowMsg},
    data() {
        return {
            currentPage: 1,
            pageSize: 10,
            total: 0,
            loading: false,
            tableData: [],
            trueValue: true,
            visible: false,
            formData: {},
            chart: null
        }
    },
    methods: {
        drawChart1() {
            this.axios({
                method: 'get',
                url: '/api/tasksSnData/getDataCount'
            }).then(res => {
                let myChart = echarts.init(document.getElementById('chart-1'));

                let option = {
                    grid: {
                        left: 50,
                        right: 40,
                        top: 50,
                        bottom: 50
                    },
                    xAxis: {
                        name: this.$t("home.day"),
                        type: 'category',
                        data: res.x
                    },
                    yAxis: {
                        name: this.$t("home.times"),
                        type: 'value',
                        min: function (value) {
                            let min = parseInt(value.min * 0.9);
                            if (min < 0) {
                                min = 0;
                            }
                            return min;
                        },
                        max: function (value) {
                            let max = parseInt(value.max * 1.1 + 10);
                            return max;
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    dataZoom: {
                        type: 'inside'
                    },
                    series: [{
                        data: res.y,
                        type: 'line',
                        lineStyle: {
                            width: 1.5
                        }
                    }]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            }).catch(error => {

            });

        },
        drawChart2() {
            this.axios({
                method: 'get',
                url: '/api/tasks/getTasksProgress',
                params: this.formData
            }).then(res => {
                let myChart = echarts.init(document.getElementById('chart-2'));
                let option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            crossStyle: {
                                color: '#999'
                            }
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: res.x,
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: this.$t('home.times'),
                        }
                    ],
                    series: [
                        {
                            name: this.$t('status.end'),
                            type: 'bar',
                            barWidth: 35,
                            data: res.y1,
                        },
                        {
                            name: this.$t('status.wait'),
                            type: 'bar',
                            barWidth: 35,
                            data: res.y2
                        },
                        {
                            name: this.$t('status.exception'),
                            type: 'bar',
                            barWidth: 35,
                            data: res.y3
                        }
                    ]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            }).catch(error => {
            });
        },
        drawChart3() {
            this.axios({
                method: 'get',
                url: '/api/msgPush/list',
                params: this.formData
            }).then(res => {
                this.total = res.total;
                this.tableData = res.rows;
            }).catch(error => {
            });
        },
        drawChart4() {
            this.axios({
                method: 'get',
                url: '/api/tasksSn/getOnlineSn',
                params: this.formData
            }).then(res => {
                let myChart = echarts.init(document.getElementById('chart-4'));
                let option = {
                    xAxis: {
                        name: this.$t('feature.tasks'),
                        type: 'category',
                        data: res.x
                    },
                    yAxis: [
                        {
                            name: this.$t('home.snNum'),
                            type: 'value'
                        }

                    ],
                    series: [
                        {
                            name: this.$t('home.snNum'),
                            data: res.y,
                            label: {
                                show: true,
                                position: 'top'
                            },
                            barMaxWidth: '35',
                            type: 'bar'

                        }
                    ]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            }).catch(error => {
                this.loading = false;
            });
        },
        openMsg(row) {
            this.$refs.showMsg.show(row)

        }
    },
    mounted() {
        this.$nextTick(function () {
            this.drawChart1();
            // this.drawChart2();
            this.drawChart3();
            // this.drawChart4();
        });
    }
}
</script>

<style scoped>

</style>
