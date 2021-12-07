<template>
    <el-container ref="tableContainer">
        <el-header style="height: auto; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-bottom: 20px; padding-top: 17px">
            <el-form :model="formData" label-width="auto" label-position="left" :inline="true" size="mini">
                <el-form-item :label="$t('system.menuResource.menuName')">
                    <el-input v-model="formData.menuName"></el-input>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="success" @click="add">{{$t('common.add')}}</el-button>
                </el-form-item>
                <el-form-item style="float: right">
                    <el-button type="primary" @click="search">{{$t('common.search')}}</el-button>
                </el-form-item>
            </el-form>
        </el-header>
        <el-container>
            <el-aside style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);margin-right: 10px">
                <el-tree ref="authTree" :data="data" :props="defaultProps" @node-click="handleNodeClick" draggable
                         @node-drop="handleDrop"
                         :allow-drop="allowDrop"
                         v-loading="loadingTree"
                         node-key="id">
                    <span class="custom-tree-node" slot-scope="{ node, data }">
                        <i v-if="data.type === 'MENU'" class="el-icon-tickets"></i>
                        <i v-if="data.type === 'FOLDER'" class="el-icon-folder-opened"></i>
                        <i v-if="data.type === 'API'" class="el-icon-position"></i>
                        <span>{{ node.label }}</span> <span style="color: rgba(128,128,128,0.59)">{{data.path}}</span>
                    </span>
                </el-tree>
            </el-aside>
            <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);text-align: center">
                <el-table
                    border
                    v-loading="loading"
                    :data="tableData"
                    style="width: 100%;min-height: 550px">
                    <el-table-column
                        prop="menuName"
                        :label="$t('system.menuResource.menuName')"
                        align="center"
                        width="280">
                    </el-table-column>
                    <el-table-column
                        prop="menuType"
                        :label="$t('system.menuResource.menuType')"
                        align="center"
                        width="280">
                    </el-table-column>
                    <el-table-column
                        prop="menuPath"
                        :label="$t('system.menuResource.menuPath')"
                        align="center">
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
        </el-container>
        <add-menu-resource @list="list" ref="addUser"/>
    </el-container>
</template>

<script>
    import AddMenuResource from './AddMenuResource'

    export default {
        name: "ListMenuResource",
        components: {AddMenuResource},
        data() {
            return {
                currentPage: 1,
                pageSize: 10,
                total: 0,
                loading: false,
                loadingTree: false,
                formData: {
                    menuType: 'API',
                    parentId: null,
                    menuName: null
                },
                tableData: [],
                data: [],
                defaultProps: {
                    children: 'child',
                    label: 'name'
                }
            }
        },
        methods: {
            search() {
                this.formData.parentId = null;
                this.formData.menuType = null;
                this.list();
            },
            list() {
                this.loading = true;
                this.formData.currentPage = this.currentPage;
                this.formData.pageSize = this.pageSize;
                this.axios({
                    method: 'get',
                    url: '/api/menuResource/list',
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
                this.$refs.addUser.show(this.formData.parentId);
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.list();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.list();
            },
            handleNodeClick(data) {
                this.formData.menuType = "API";
                this.formData.menuName = null;
                this.formData.parentId = data.id;
                this.list();
            },
            handleDrop(draggingNode, dropNode, dropType, ev) {

                const data = {
                    parentId: draggingNode.data.parentId,
                    draggingNodeId: draggingNode.data.id,
                    dropNodeId: dropNode.data.id,
                    type: dropType
                };

                this.axios({
                    method: 'post',
                    url: '/api/menuResource/updateMenuResourceOrder',
                    data: this.$qs.stringify(data)
                }).then(res => {
                    this.$message({
                        message: this.$t('common.operateSuccessfully'),
                        type: 'success'
                    });
                }).catch(error => {});
            },
            allowDrop(draggingNode, dropNode, type) {
                return dropNode.data.parentId===draggingNode.data.parentId && type !== 'inner';
            }
        },
        mounted() {
            this.$nextTick(function () {
                this.loadingTree = true;
                this.formData.parentId = null;
                this.formData.menuType = null;
                this.formData.menuType = "API";

                this.list();

                this.axios({
                    method: 'get',
                    url: '/api/menuResource/getTreeData'
                }).then(res => {
                    this.data = res.resources;
                    this.loadingTree = false;
                }).catch(error => {
                    this.loadingTree = false;
                });
            })
        }
    }
</script>

<style scoped>

</style>
