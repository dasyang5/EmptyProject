<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item :label="$t('system.user.username')">
                    <el-input v-model="formData.username"></el-input>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button v-if="$common.checkAuth('/api/user/add')" type="success" @click="add">{{$t('common.add')}}</el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" @click="search">{{$t('common.search')}}</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); text-align: center; padding-top: 17px;">
            <el-table
                border
                v-loading="loading"
                :data="tableData"
                style="width: 100%;min-height: 550px">
                <el-table-column
                    prop="username"
                    :label="$t('system.user.username')"
                    align="center">
                </el-table-column>
                <el-table-column
                    :label="$t('system.user.organization')"
                    align="center">
                    <template slot-scope="scope">
                        {{ scope.row.organ === null ? '':scope.row.organ.organName }}
                    </template>
                </el-table-column>
                <el-table-column
                    align="center"
                    width="150"
                    :label="$t('system.user.passwordErrorTime')">
                    <template slot-scope="scope">
                        {{ scope.row.errorPwdTime === null ? '0':scope.row.errorPwdTime }}
                    </template>
                </el-table-column>
                <el-table-column
                    align="center"
                    width="150"
                    :label="$t('system.user.status')">
                    <template slot-scope="scope">
                        <el-tag v-if="!scope.row.enabled" type="danger">禁用</el-tag>
                        <el-tag v-else-if="!scope.row.accountNonLocked" type="warning">锁定</el-tag>
                        <el-tag v-else type="success">正常</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="loginTime"
                    align="center"
                    width="280"
                    :label="$t('system.user.loginTime')">
                </el-table-column>
                <el-table-column
                    prop="updTime"
                    align="center"
                    width="280"
                    :label="$t('common.updateTime')">
                </el-table-column>
                <el-table-column
                    fixed="right"
                    align="center"
                    width="280"
                    :label="$t('common.operator')">
                    <template slot-scope="scope">
                        <el-button class="table-button" type="primary" size="mini" plain icon="el-icon-refresh-left" @click="reset(scope.row)">{{$t('system.user.reset')}}</el-button>
                        <el-button class="table-button" v-if="!scope.row.enabled" type="warning" size="mini" plain icon="el-icon-unlock" @click="enable(scope.row)">{{$t('system.user.enable')}}</el-button>
                        <el-button class="table-button" v-else type="warning" size="mini" plain icon="el-icon-lock" @click="disable(scope.row)">{{$t('system.user.disable')}}</el-button>
<!--                        <el-button @click="remove(scope.row)" v-if="scope.row.username === 'admin'" disabled="" type="text" size="small">{{$t('common.delete')}}</el-button>-->
<!--                        <el-button @click="remove(scope.row)" v-else type="text" size="small">{{$t('common.delete')}}</el-button>-->
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                style="margin-top: 20px"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20, 50, 100]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </el-main>
        <add-user @list="list" ref="addUser"/>
    </el-container>
</template>

<script>
    import AddUser from '@/components/system/user/AddUser'

    export default {
        name: "ListUser",
        components: {AddUser},
        data() {
            return {
                currentPage: 1,
                pageSize: 10,
                loading: false,
                total: 0,
                formData: {
                    username: ''
                },
                tableData: []
            }
        },
        methods: {
            /**
             * 点击搜索按钮
             */
            search() {
                this.list();
            },
            /**
             * 查询数据
             */
            list() {
                this.loading = true;
                this.formData.currentPage = this.currentPage;
                this.formData.pageSize = this.pageSize;
                this.axios({
                    method: 'get',
                    url: '/api/user/list',
                    params: this.formData,
                }).then(res => {
                    this.loading = false;
                    this.total = res.total;
                    this.tableData = res.rows;
                }).catch(error => {
                    this.loading = false;
                });
            },
            /**
             * 新增数据
             */
            add() {
                this.$refs.addUser.show();
            },
            reset(row) {
                this.$confirm(this.$t('system.user.resetHit'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'post',
                        url: '/api/user/reset',
                        params: {userId: row.userId}
                    }).then(res => {
                        this.$message({
                            message: this.$t('common.operateSuccessfully'),
                            type: 'success'
                        });
                        this.list();
                    }).catch(error => {});
                }).catch(() => {

                });
            },
            disable(row) {
                this.$confirm(this.$t('system.user.disableHit'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'post',
                        url: '/api/user/disable',
                        params: {userId: row.userId}
                    }).then(res => {
                        this.$message({
                            message: this.$t('common.operateSuccessfully'),
                            type: 'success'
                        });
                        this.list();
                    }).catch(error => {});
                }).catch(() => {

                });
            },
            enable(row) {
                this.$confirm(this.$t('system.user.enableHit'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'post',
                        url: '/api/user/enable',
                        params: {userId: row.userId}
                    }).then(res => {
                        this.$message({
                            message: this.$t('common.operateSuccessfully'),
                            type: 'success'
                        });
                        this.list();
                    }).catch(error => {});
                }).catch(() => {

                });
            },
            /**
             * 删除数据
             * @param row
             */
            remove(row) {
                this.$confirm(this.$t('common.deleteConfirmMsg'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'delete',
                        url: '/api/user/delete',
                        params: {userId: row.userId}
                    }).then(res => {
                        this.$message({
                            message: this.$t('common.operateSuccessfully'),
                            type: 'success'
                        });
                        this.list();
                    }).catch(error => {});
                }).catch(() => {

                });
            },
            /**
             * 改变 pageSize 回调
             * @param val
             */
            handleSizeChange(val) {
                this.pageSize = val;
                this.list();
            },
            /**
             * 改变 page 回调
             * @param val
             */
            handleCurrentChange(val) {
                this.currentPage = val;
                this.list();
            }
        },
        mounted() {
            this.$nextTick(function () {
                this.list();
            })
        }
    }
</script>

<style scoped>

</style>
