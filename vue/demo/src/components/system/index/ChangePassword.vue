<template>
    <el-dialog
        :title="$t('system.index.changePassword')"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        width="40%">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="auto" class="demo-ruleForm">
            <el-form-item :label="$t('changePassword.currentPassword')" prop="oldPass">
                <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$t('changePassword.newPassword')" prop="pass">
                <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item :label="$t('changePassword.confirmPassword')" prop="checkPass">
                <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="handleClose">{{$t('common.cancel')}}</el-button>
            <el-button type="primary" @click="submitForm">{{$t('common.confirm')}}</el-button>
        </span>
    </el-dialog>
</template>

<script>
    export default {
        data() {
            var validateOldPass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('changePassword.passwordHit1')));
                }
                callback();
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('changePassword.passwordHit1')));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error(this.$t('changePassword.passwordHit2')));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error(this.$t('changePassword.passwordHit3')));
                } else {
                    callback();
                }
            };
            return {
                dialogVisible: false,
                ruleForm: {
                    pass: '',
                    checkPass: '',
                    oldPass: ''
                },
                rules: {
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    oldPass: [
                        { validator: validateOldPass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            show() {
                this.dialogVisible = true;
            },
            submitForm() {
                this.$refs.ruleForm.validate((valid) => {
                    if (valid) {
                        this.axios({
                            method: 'post',
                            url: '/api/user/changePassword',
                            data: this.$qs.stringify(this.ruleForm)
                        }).then(res => {
                            this.$message({
                                message: this.$t('common.operateSuccessfully'),
                                type: 'success'
                            });
                            this.handleClose();
                        }).catch(error => {});
                    } else {
                        return false;
                    }
                });
            },
            handleClose() {
                this.$refs.ruleForm.resetFields();
                this.dialogVisible = false;
            }
        },
        mounted() {
        }
    };
</script>

<style scoped>

</style>
