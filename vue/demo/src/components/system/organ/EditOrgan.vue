<template>
    <el-dialog :title="$t('system.role.editRole')" :visible.sync="visible">
        <el-form ref="form" :model="formData" label-width="100px" size="mini" style="width: 80%; margin: auto" label-position="left">
            <el-form-item :label="$t('system.organ.organName')">
                <el-input v-model="formData.organName"></el-input>
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
                    organId: '',
                    organName: ''
                },
                visible: false
            }
        },
        methods: {
            show(organId) {
                this.formData.organId = organId;

                this.formData.organName = '';

                this.visible = true;

                this.axios({
                    method: 'get',
                    url: '/api/organ/get',
                    params: {organId: organId}
                }).then(res => {
                    const organ = res.organ;
                    this.formData.organName = organ.organName;
                }).catch(error => {});

            },
            save() {
                this.axios({
                    method: 'post',
                    url: '/api/organ/edit',
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
