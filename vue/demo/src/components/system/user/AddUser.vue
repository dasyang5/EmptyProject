<template>
    <el-dialog :title="$t('system.user.addUser')" :visible.sync="visible">
        <el-form ref="form" :model="formData" :rules="rules" label-width="auto" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.user.username')" prop="username">
                <el-input v-model="formData.username"></el-input>
            </el-form-item>
            <el-form-item :label="$t('system.user.organization')" prop="organId">
                <el-select v-model="formData.organId" :placeholder="$t('common.pleaseChoose')" style="width: 100%">
                    <el-option
                        v-for="item in organList"
                        :key="item.organId"
                        :label="item.organName"
                        :value="item.organId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item :label="$t('system.user.role')" prop="roleId">
                <el-select v-model="formData.roleId" :placeholder="$t('common.pleaseChoose')" style="width: 100%">
                    <el-option
                        v-for="item in roleList"
                        :key="item.roleId"
                        :label="item.roleName"
                        :value="item.roleId">
                    </el-option>
                </el-select>
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
        name: "AddUser",
        computed: {
            rules() {
                return {
                    username: [
                        { required: true, message: this.$t('common.pleaseInputHit', [this.$t('system.user.username')]), trigger: 'blur' },
                        { min: 3, max: 30, message: this.$t('common.valueLengthLimitHit', [3, 30]), trigger: 'blur' }
                    ],
                    organId: [
                        { required: true, message: this.$t('common.pleaseChooseHit', [this.$t('system.user.organization')]), trigger: 'change' }
                    ],
                    roleId: [
                        { required: true, message: this.$t('common.pleaseChooseHit', [this.$t('system.user.role')]), trigger: 'change' }
                    ]
                }
            }
        },
        data() {
            return {
                formData: {
                    username: '',
                    organId: '',
                    roleId: ''
                },
                organList: [],
                roleList: [],
                visible: false
            }
        },
        methods: {
            show() {
                this.organList = [];
                this.roleList = [];
                this.formData = {
                    username: '',
                    organId: '',
                    roleId: ''
                };

                //获取机构
                this.axios({
                    method: 'get',
                    url: '/api/organ/findOrganList',
                }).then(res => {
                    this.organList = res.rows;
                    if (this.organList.length > 0) {
                        this.formData.organId = this.organList[0].organId;
                    }
                }).catch(error => {});

                //获取角色
                this.axios({
                    method: 'get',
                    url: '/api/role/findRoleList',
                }).then(res => {
                    this.roleList = res.rows;
                    this.roleList = this.roleList.filter(a => {
                        return a.roleAuthKey !== "admin";
                    });

                    if (this.roleList.length > 0) {
                        this.formData.roleId = this.roleList[0].roleId;
                    }
                }).catch(error => {});

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
                            url: '/api/user/add',
                            data: this.$qs.stringify(this.formData)
                        }).then(res => {
                            this.$message({
                                message: this.$t('common.operateSuccessfully'),
                                type: 'success'
                            });
                            this.visible = false;
                            this.$emit('list');
                        }).catch(error => {});
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
