<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto;margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item>
                    <el-button v-if="$common.checkAuth('/api/organ/add')" type="primary" @click="add">{{$t('common.add')}}</el-button>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="formData.organName" :placeholder="$t('system.organ.organName')">
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
                    prop="organName"
                    :label="$t('system.organ.organName')"
                    align="center">
                </el-table-column>
                <el-table-column
                    prop="creTime"
                    :label="$t('common.createTime')"
                    align="center">
                </el-table-column>
                <el-table-column
                    align="center"
                    width="200"
                    :label="$t('common.operator')">
                    <template slot-scope="scope">
                        <el-button class="table-button" type="text" size="mini" plain icon="el-icon-delete" @click="remove(scope.row)">{{$t('common.delete')}}</el-button>
                        <el-button class="table-button" type="text" size="mini" plain icon="el-icon-edit" @click="edit(scope.row)">{{$t('common.edit')}}</el-button>
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
        <add-organ @list="list" ref="addOrgan"/>
        <edit-organ @list="list" ref="editOrgan"/>
    </el-container>
</template>

<script>
    import AddOrgan from './AddOrgan'
    import EditOrgan from './EditOrgan'
    export default {
        components: {AddOrgan, EditOrgan},
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
                    url: '/api/organ/list',
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
                this.$refs.addOrgan.show();
            },
            edit(row) {
                this.$refs.editOrgan.show(row.organId);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.list();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.list();
            },
            remove(row) {
                this.$confirm(this.$t('common.deleteConfirmMsg'), this.$t('common.hit'), {
                    confirmButtonText: this.$t('common.confirm'),
                    cancelButtonText: this.$t('common.cancel'),
                    type: 'warning'
                }).then(() => {
                    this.axios({
                        method: 'delete',
                        url: '/api/organ/delete',
                        params: {organId: row.organId}
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
