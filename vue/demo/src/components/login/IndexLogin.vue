<template>
    <div style="height: 100%;width: 100%" :style="loginContainerStyle">
        <el-row style="border-bottom:1px solid rgba(0,0,0,0.17); background-color: white">
            <el-col :span="2" :offset="1" style="padding: 15px 5px 5px 5px; cursor: pointer" @click.native="backHome">
                <span><img class="logo" src="@/assets/images/logo.png"></span>
            </el-col>
            <el-col :span="20" style="padding: 15px 5px 5px 5px; line-height: 40px; font-size: 17px; margin-left: 20px">
                {{$t('login.login')}}
            </el-col>
        </el-row>
        <el-row style="padding-top: 15%">
            <el-col :span="7" :offset="13">
                <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);box-sizing: border-box; padding: 20px; background-color: white; border-radius: 5px">
                    <div style="text-align: center;font-size: 20px;line-height: 50px">
                        {{$t('login.title')}}
                    </div>
                    <el-input type="text" v-model="loginForm.username" :placeholder="$t('login.username')" style="margin: 20px 0"/>
                    <el-input type="password" v-model="loginForm.password" :placeholder="$t('login.password')" show-password style="margin: 20px 0"/>
                    <!--错误提示-->
                    <div id="error-msg" style="height: 15px;line-height: 15px;font-size: 13px; color: red">{{errorMsg}}</div>
                    <!--登录-->
                    <el-button :loading="loginStatus" style="width: 100%; margin: 20px 0" @click="login" type="primary">{{$t('login.login')}}</el-button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {mapMutations} from 'vuex';
    export default {
        name: "IndexLogin",
        data() {
            return {
                loginForm: {
                    username: '',
                    password: ''
                },
                loginContainerStyle: {
                    backgroundImage: 'url(' + require('@/assets/images/home/login_bg.jpg') + ')',
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: '100% 100%'
                },
                errorMsg: null,
                loginStatus: false
            };
        },
        methods: {
            ...mapMutations(['changeLogin']),
            login() {
                this.loginStatus = true;
                this.errorMsg = null;
                let _this = this;
                if (this.loginForm.username === '' || this.loginForm.password === '') {
                    this.loginStatus = false;
                    this.showLoginErrorMsg(this.$t('login.loginHit1'));
                } else {
                    this.axios({
                        method: 'post',
                        url: '/login',
                        data: this.$qs.stringify(_this.loginForm)
                    }).then(res => {
                        this.loginStatus = false;
                        _this.userToken = 'Bearer ' + res.token;
                        // 将用户token保存到vuex中
                        localStorage.setItem("auth", JSON.stringify(res.auth));
                        localStorage.setItem('Authorization', _this.userToken);
                        localStorage.setItem('username', res.user.username);
                        _this.$router.push('/system/home');
                    }).catch(error => {
                        this.loginStatus = false;
                        this.showLoginErrorMsg(this.$t(error.msg));
                    });
                }
            },
            showLoginErrorMsg(msg){
                this.errorMsg = msg;
                // this.$message.error(msg);
            },
            backHome() {
                this.$parent.hideLogin();
            }
        },
        mounted() {
            let _this = this;
            document.onkeydown = function(e){
                if (window.event.keyCode === 13) {
                    _this.login();
                }
            }
        }
    }
</script>

<style lang="scss">
    .logo {
        width: 100%;
        vertical-align: middle;
    }
</style>
