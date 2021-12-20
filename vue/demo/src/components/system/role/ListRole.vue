<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto;margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item>
                    <el-button v-if="$common.checkAuth('/api/role/add')" type="primary" @click="add">{{$t('common.add')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="formData.roleName" :placeholder="$t('system.role.roleName')">
                        <el-button slot="append" icon="el-icon-search" @click="list"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item style="float: right;">
                    <el-button icon="el-icon-refresh-left" circle @click="list"></el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main style="text-align: center; padding-top: 17px;">
            <el-table
                class="customerTable"
                v-loading="loading"
                :data="tableData"
                style="width: 100%;min-height: 560px">
                <el-table-column
                    prop="roleName"
                    :label="$t('system.role.roleName')"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="creTime"
                    :label="$t('common.createTime')"
                    align="center">
                </el-table-column>
                <el-table-column
                    align="center"
                    width="260"
                    :label="$t('common.operator')">
                    <template slot-scope="scope">
                        <el-button type="text" plain icon="el-icon-delete" v-if="scope.row.roleName === 'admin' || scope.row.roleName === 'user'" disabled="" size="mini">{{$t('common.delete')}}</el-button>
                        <el-button type="text" plain icon="el-icon-delete" v-else @click="remove(scope.row)" size="mini">{{$t('common.delete')}}</el-button>
                        <el-button type="text" plain icon="el-icon-edit" size="mini" @click="edit(scope.row)">{{$t('common.edit')}}</el-button>
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
        <add-role @list="list" ref="addRole"/>
        <edit-role @list="list" ref="editRole"/>
    </el-container>
</template>

<script>
    import AddRole from './AddRole'
    import EditRole from './EditRole'
    export default {
        name: "ListRole",
        components: {AddRole, EditRole},
        data() {
            return {
                currentPage: 1,
                pageSize: 10,
                loading: false,
                total: 0,
                formData: {
                    menuName: null
                },
                tableData: []
            }
        },
        methods: {
            list() {
                this.loading = true;
                this.formData.currentPage = this.currentPage;
                this.formData.pageSize = this.pageSize;
                this.axios({
                    method: 'get',
                    url: '/api/role/list',
                    params: this.formData
                }).then(res => {
                    this.loading = false;
                    this.total = res.total;
                    this.tableData = res.rows;
                }).catch(error => {
                    this.loading = false;
                });
            },
            add() {
                this.$refs.addRole.show();
            },
            edit(row) {
                this.$refs.editRole.show(row.roleId);
            },
            remove(row) {
                this.$confirm(this.$t('common.deleteConfirmMsg'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'delete',
                        url: '/api/role/delete',
                        params: {roleId: row.roleId}
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
            handleSizeChange(val) {
                this.pageSize = val;
                this.list();
            },
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
