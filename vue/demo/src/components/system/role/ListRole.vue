<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item :label="$t('system.role.roleName')">
                    <el-input v-model="formData.roleName"></el-input>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="success" @click="add">{{$t('common.add')}}</el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" @click="search">{{$t('common.search')}}</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); text-align: center; padding-top: 17px;">
            <el-row style="min-height: 550px" v-loading="loading">
                <el-col :span="6" v-for="role in tableData" :key="role.roleId">
                    <el-card style="margin: 30px">
                       <!-- <div slot="header">
                            <span>{{role.roleName}}</span>
                        </div>-->
                        <div>
                            <img style="width: 130px" class="home-image" src="@/assets/images/system/role/role.png">
                        </div>
                        <div style="font-size: 20px">
                            {{role.roleName}}
                        </div>
                        <el-divider></el-divider>
                        <el-row style="margin-top: 10px">
                            <el-col :span="12" style="padding: 5px">
                                <el-button style="float: right; padding: 5px 0;width: 100%;" type="danger" icon="el-icon-delete" v-if="role.roleName === 'admin' || role.roleName === 'user'" disabled="" size="mini">{{$t('common.delete')}}</el-button>
                                <el-button style="float: right; padding: 5px 0;width: 100%;" type="danger" icon="el-icon-delete" v-else @click="remove(role)" size="mini">{{$t('common.delete')}}</el-button>
                            </el-col>
                            <el-col :span="12" style="padding: 5px">
                                <el-button style="float: right; padding: 5px 0;width: 100%;" type="primary" icon="el-icon-edit" size="mini" @click="edit(role)">{{$t('common.edit')}}</el-button>
                            </el-col>
                        </el-row>
                    </el-card>
                </el-col>
            </el-row>

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
            search() {
                this.list();
            },
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
