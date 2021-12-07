<template>
    <el-dialog :title="$t('system.menuResource.addResource')" :visible.sync="visible">
        <el-form ref="form" :model="formData" label-width="auto" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.menuResource.menuName')">
                <el-input v-model="formData.menuName"></el-input>
            </el-form-item>
            <el-form-item :label="$t('system.menuResource.menuNameEn')">
                <el-input v-model="formData.menuNameEn"></el-input>
            </el-form-item>
            <el-form-item :label="$t('system.menuResource.menuType')">
                <el-select v-model="formData.menuType" style="width: 100%" @change="changeMenuType">
                    <el-option
                        v-for="item in menuTypes"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item :label="$t('system.menuResource.parent')">
                <el-select v-model="parentId" style="width: 100%">
                    <el-option label="" value="无"></el-option>
                    <el-option
                        v-for="item in parentList"
                        :key="item.menuResourceId"
                        :label="item.menuName"
                        :value="item.menuResourceId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item :label="$t('system.menuResource.menuPath')">
                <el-input v-model="formData.menuPath"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false" size="mini">{{$t('common.cancel')}}</el-button>
            <el-button type="primary" @click="add" size="mini">{{$t('common.confirm')}}</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "AddMenuResource",
        data() {
            return {
                parentId: '',
                formData: {
                    menuName: '',
                    parentId: '',
                    menuOrder: '',
                    menuType: '',
                    menuPath: ''
                },
                visible: false,
                menuTypes: [{
                    value: 'API',
                    label: 'API'
                }, {
                    value: 'MENU',
                    label: 'MENU'
                }, {
                    value: 'FOLDER',
                    label: 'FOLDER'
                }],
                parentList: [],
                allParents: []
            }
        },
        methods: {
            show(parentId) {
                this.parentId = parentId;
                this.visible = true;

                this.$nextTick(() => {
                    if (this.$refs.form !== undefined) {
                        this.$refs.form.resetFields();
                    }
                });

                this.axios({
                    method: 'get',
                    url: '/api/menuResource/findParentList',
                }).then(res => {
                    this.allParents = res.rows;
                    this.parentList = res.rows;
                }).catch(error => {});

            },
            hide() {
                this.visible = false;
            },
            add() {
                this.formData.parentId = this.parentId;

                this.axios({
                    method: 'post',
                    url: '/api/menuResource/add',
                    data: this.$qs.stringify(this.formData)
                }).then(res => {
                    this.$message({
                        message: this.$t('common.operateSuccessfully'),
                        type: 'success'
                    });
                    this.$emit('list');
                    this.visible = false;
                }).catch(error => {});
            },
            changeMenuType() {
                if (this.formData.menuType === 'API') {
                    this.parentList = this.allParents.filter(a => a.menuType === 'MENU');
                } else{
                    this.parentList = this.allParents.filter(a => a.menuType === 'FOLDER');
                }
                this.formData.parentId = '';
            }
        }
    }
</script>

<style scoped>

</style>
