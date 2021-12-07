<template>
    <el-dialog :title="$t('system.role.addRole')" :visible.sync="visible">
        <el-form ref="form" :model="formData" :rules="rules" label-width="auto" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.role.roleName')" prop="roleName">
                <el-input v-model="formData.roleName"></el-input>
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
        name: "AddRole",
        computed: {
            rules() {
                return {
                    roleName: [
                        { required: true, message: this.$t('common.pleaseInputHit', [this.$t('system.role.roleName')]), trigger: 'blur' },
                        { min: 3, max: 30, message: this.$t('common.valueLengthLimitHit', [3, 30]), trigger: 'blur' }
                    ]
                }
            }
        },
        data() {
            return {
                formData: {
                    roleName: '',
                    roleAuthKey: ''
                },
                visible: false
            }
        },
        methods: {
            show() {
                this.$nextTick(() => {
                    if (this.$refs.form !== undefined) {
                        this.$refs.form.resetFields();
                    }
                });
                this.visible = true;
            },
            hide() {
                this.visible = false;
            },
            add() {
                this.$refs.form.validate((valid) => {
                    if (!valid) {
                        return false;
                    } else {
                        this.axios({
                            method: 'post',
                            url: '/api/role/add',
                            data: this.$qs.stringify(this.formData)
                        }).then(res => {
                            this.$message({
                                message: this.$t('common.operateSuccessfully'),
                                type: 'success'
                            });
                            this.$emit('list');
                            this.visible = false;
                        }).catch(error => {});
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
