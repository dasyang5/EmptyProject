<template>
    <el-dialog :title="$t('system.organ.addOrgan')" :visible.sync="visible">
        <el-form ref="form" :model="formData" label-width="auto" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.organ.organName')">
                <el-input v-model="formData.organName"></el-input>
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
                formData: {
                    organName: ''
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
                this.axios({
                    method: 'post',
                    url: '/api/organ/add',
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
        }
    }
</script>

<style scoped>

</style>
