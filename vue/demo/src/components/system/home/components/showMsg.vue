<template>
    <el-drawer :title="$t('home.msgList') " :visible.sync="visible" append-to-body size="80%">
        <div class="el-dialog-div">
            <el-container ref="tableContainer" style="height: 100%">
                <el-header
                    style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px;">
                </el-header>
                <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); text-align: center; padding-top: 17px;height: 80%">
                    <el-descriptions :title="$t('home.msgDetail')">
                        <el-descriptions-item :label="$t('home.msgTitle')">{{this.msg.title}}</el-descriptions-item>
                        <el-descriptions-item :label="$t('home.msgDetail')">{{this.msg.msg}}</el-descriptions-item>
                    </el-descriptions>
                </el-main>
            </el-container>
        </div>
    </el-drawer>
</template>

<script>



export default {
    name: "showMsg",
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
            msg: '',
            chart: null
        }
    },
    methods: {
        show(row) {

            this.visible = true;
            this.msg = row
            if (row.isRead==="N"){
                this.changeStatus(row.msgPushId);
            }

        },
        changeStatus(msgPushId) {


            this.axios({
                method: 'post',
                url: '/api/msgPush/updateIsRead',
                params: {"msgPushId":msgPushId}
            }).then(res => {

            }).catch(error => {
                this.loading = false;
            });
        },
        openMsg(row){
            console.log(row)

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
