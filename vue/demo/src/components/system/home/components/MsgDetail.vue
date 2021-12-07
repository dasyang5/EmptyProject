<template>
    <el-drawer :title="$t('home.msgList') " :visible.sync="visible" append-to-body size="80%">
        <div class="el-dialog-div">
            <el-container ref="tableContainer" style="height: 100%">
                <el-header
                    style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px;">
                </el-header>
                <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); text-align: center; padding-top: 17px;height: 80%">
                    <el-table
                        border
                        v-loading="loading"
                        lazy
                        :data="tableData"
                        style="width: 100%;min-height: 550px">
                        <el-table-column
                            prop="title"
                            :label="$t('home.msgTitle')"
                            align="center">
                            <template slot-scope="scope">
                                <el-button @click="openMsg(scope.row)" type="text" size="mini">
                                    {{scope.row.title}}
                                </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column
                            fixed="right"
                            align="center"
                            :label="$t('home.msgType')"
                            width="200">
                            <template slot-scope="scope">
                                <fragment>
                                    <label v-if="scope.row.msgType === 'TASK-FINISH'">{{$t('status.taskFinish')}}</label>
                                    <label v-else-if="scope.row.msgType === 'CASE-FINISH'">{{$t('status.caseFinish')}}</label>
                                    <label v-else-if="scope.row.msgType === 'EXEC-EXCEPTION'">{{$t('status.execException')}}</label>
                                    <label v-else-if="scope.row.msgType === 'TIMEOUT-EXCEPTION'">{{$t('status.timeoutException')}}</label>
                                    <label v-else-if="scope.row.msgType === 'INTERVAL-EXCEPTION'">{{$t('status.intervalException')}}</label>
                                </fragment>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="creTime"
                            :label="$t('common.createTime')"
                            align="center">
                        </el-table-column>

                    </el-table>
                </el-main>
                <show-msg ref="showMsg"></show-msg>
            </el-container>
        </div>
    </el-drawer>
</template>

<script>



import ShowMsg from "./showMsg";
export default {
    name: "MsgDetail",
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
            formData: {

            },
            chart: null
        }
    },
    methods: {
        show() {

            this.visible = true;
            this.list();

        },
        list() {
            this.loading = true;
            this.formData.isRead = "N";
            this.axios({
                method: 'get',
                url: '/api/msgPush/list',
                params: this.formData
            }).then(res => {
                this.loading = false;
                this.total = res.total;
                this.tableData = res.rows;
            }).catch(error => {
                this.loading = false;
            });
        },
        openMsg(row){
            this.$refs.showMsg.show(row)

        }

    }
}
</script>

<style lang="scss" scoped>
.el-dialog-div {
    height: 80vh;
    overflow: auto;
}
</style>
