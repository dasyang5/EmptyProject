<template>
    <el-dialog :title="$t('system.role.editRole')" :visible.sync="visible">
        <el-form ref="form" :model="formData" label-width="100px" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.role.roleName')">
                <el-input v-model="formData.roleName"></el-input>
            </el-form-item>
            <el-form-item :label="$t('system.role.roleAuth')">
                <el-tree ref="authTree" :data="data" :props="defaultProps" show-checkbox node-key="id" :default-checked-keys="checkedKeys" style="height: 300px; overflow-y: auto">
                    <span class="custom-tree-node" slot-scope="{ node, data }">
                        <i v-if="data.type === 'MENU'" class="el-icon-tickets"></i>
                        <i v-if="data.type === 'FOLDER'" class="el-icon-folder-opened"></i>
                        <i v-if="data.type === 'API'" class="el-icon-position"></i>
                        <span>{{ node.label }}</span>
                    </span>
                </el-tree>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false" size="mini">{{$t('common.cancel')}}</el-button>
            <el-button type="primary" @click="save" size="mini">{{$t('common.confirm')}}</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "EditRole",
        data() {
            return {
                formData: {
                    roleId: '',
                    roleName: '',
                    roleAuthKey: ''
                },
                visible: false,
                data: [],
                defaultProps: {
                    children: 'child',
                    label: 'name'
                },
                checkedKeys: []
            }
        },
        methods: {
            show(roleId) {
                this.formData.roleId = roleId;

                this.data = [];
                this.formData.roleName = '';
                this.formData.roleAuthKey = '';

                this.visible = true;

                this.axios({
                    method: 'get',
                    url: '/api/role/get',
                    params: {roleId: roleId}
                }).then(res => {
                    this.data = res.resources;
                    this.checkedKeys = res.checkedKey;
                    const role = res.role;
                    this.formData.roleName = role.roleName;
                    this.formData.roleAuthKey = role.roleAuthKey;
                }).catch(error => {});

            },
            save() {
                this.formData.auths = this.$refs.authTree.getCheckedNodes();
                this.formData.halfAuths = this.$refs.authTree.getHalfCheckedNodes();
                this.axios({
                    method: 'post',
                    url: '/api/role/edit',
                    data: this.formData
                }).then(res => {
                    this.$message({
                        message: this.$t('common.operateSuccessfully'),
                        type: 'success'
                    });
                    this.visible = false;
                    this.$emit('list');
                }).catch(error => {});
            }
        }
    }
</script>

<style scoped>

</style>
