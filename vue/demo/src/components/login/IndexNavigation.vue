<template>
  <div>
    <el-row class="index-header">
      <el-col :span="2" :offset="1" style="padding: 15px 5px 5px 5px">
        <span><img class="logo" src="@/assets/images/logo_new.png"></span>
      </el-col>
      <el-col :span="20">
        <el-menu v-if="language==='zh'" text-color="#303133" active-text-color="#303133" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-submenu index="product">
            <template #title>产品中心</template>
            <el-menu-item index="https://www.cn.newlandpayment.com/show-list-13141-1.html">智能POS</el-menu-item>
            <el-menu-item index="https://www.cn.newlandpayment.com/show-list-13145-1.html">移动支付</el-menu-item>
            <el-menu-item index="https://www.cn.newlandpayment.com/show-list-13143-1.html">传统POS</el-menu-item>
          </el-submenu>
          <el-menu-item index="https://www.cn.newlandpayment.com/news-list-7923-1.html">解决方案</el-menu-item>
          <el-menu-item index="https://www.cn.newlandpayment.com/about-list-2719-1.html">服务中心</el-menu-item>
          <el-menu-item index="https://www.cn.newlandpayment.com/about-list-2697-1.html">关于我们</el-menu-item>
          <el-menu-item index="login" class="right-menu">{{ $t('login.login') }}</el-menu-item>
          <el-submenu index="language" class="right-menu">
            <template #title>{{ language === 'zh' ? '简体中文':'English' }}</template>
            <el-menu-item index="zh">简体中文</el-menu-item>
            <el-menu-item index="en">English</el-menu-item>
          </el-submenu>
        </el-menu>
        <el-menu v-if="language!=='zh'" text-color="#303133" active-text-color="#303133" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-submenu index="product">
            <template #title>Products</template>
            <el-menu-item index="https://www.newlandpayment.com/show-article-83273.html">Android POS</el-menu-item>
            <el-menu-item index="https://www.newlandpayment.com/show-article-83287.html">Conventional POS</el-menu-item>
            <el-menu-item index="https://www.newlandpayment.com/show-list-12747-1.html#mpos02">Mobile Payment Device</el-menu-item>
            <el-menu-item index="https://www.newlandpayment.com/show-article-83107.html">Smart Cash Register</el-menu-item>
            <el-menu-item index="https://www.newlandpayment.com/show-article-83299.html">PINPAD</el-menu-item>
          </el-submenu>
          <el-menu-item index="https://www.newlandpayment.com/news-list-7769-1.html">Solutions</el-menu-item>
          <el-menu-item index="https://www.newlandpayment.com/news-list-7763-1.html">Support</el-menu-item>
          <el-menu-item index="https://www.newlandpayment.com/about-list-2565-1.html">About NPT</el-menu-item>
          <el-menu-item index="login" class="right-menu">{{ $t('login.login') }}</el-menu-item>
          <el-submenu index="language" class="right-menu">
            <template #title>{{ language === 'zh' ? '简体中文':'English' }}</template>
            <el-menu-item index="zh">简体中文</el-menu-item>
            <el-menu-item index="en">English</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
export default {
  name: 'IndexNavigation',
  data() {
    return {
      activeIndex: '',
      loginFormVisible: false,
      loginForm: {
        loginName: '',
        password: '',
        captchaVerification: ''
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  computed: {
      language() {
          return this.$i18n.locale;
      }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    handleSelect(key, keyPath) {
        if (keyPath[0] === 'language') {
            // 切换语言
            this.$i18n.locale = key;
            Cookies.set('language', key);
            localStorage.setItem('language', key);
        } else if (keyPath[0] === 'login') {
            // 登录窗口切换
            this.$parent.showLogin();
        } else {
            window.open(key, '_blank')
        }
    }
  }
}
</script>

<style lang="scss">
  .index-header{
    .el-row{
      padding: 0;
      margin: 0;
    }
    .right-menu{
      float: right !important;
    }
    .logo {
      width: 100%;
      vertical-align: middle;
        margin-top: 2px;
    }
    .el-menu--horizontal>.el-menu-item.is-active, .el-menu--horizontal>.el-submenu.is-active .el-submenu__title{
      border-bottom: none;
    }
  }

</style>
