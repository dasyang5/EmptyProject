<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item :label="$t('system.organ.organName')">
                    <el-input v-model="formData.organName"></el-input>
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
                <el-col :span="6" v-for="obj in tableData" :key="obj.organId">
                    <el-card style="margin: 30px">
                        <!-- <div slot="header">
                             <span>{{role.roleName}}</span>
                         </div>-->
                        <div>
                            <img style="width: 180px; height: 150px" class="home-image" src="@/assets/images/system/organ/organization.png">
                        </div>
                        <div style="font-size: 20px">
                            {{obj.organName}}
                        </div>
                        <el-row style="margin-top: 10px">
                            <el-col :span="12" style="padding: 5px">
                                <el-button style="float: right; padding: 5px 0;width: 100%;" type="danger" icon="el-icon-delete" @click="remove(obj)" size="mini">{{$t('common.delete')}}</el-button>
                            </el-col>
                            <el-col :span="12" style="padding: 5px">
                                <el-button style="float: right; padding: 5px 0;width: 100%;" type="primary" icon="el-icon-edit" size="mini" @click="edit(obj)">{{$t('common.edit')}}</el-button>
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
            search() {
                this.list();
            },
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
