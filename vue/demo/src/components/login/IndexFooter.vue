<template>
  <div class="index-footer">
    <el-row>
      <el-col :span="8" style="text-align: center;padding-top: 40px">
        <a class="navbar-brand mr-lg-5" style="width: 142px;cursor:pointer">
          <img alt="Footer logo" width="142px" src="@/assets/images/home/logo.png">
        </a>
        <ul class="group-info">
          <li>{{$t('login.hotLine')}}: 4009-181-311</li>
        </ul>
      </el-col>
      <el-col v-if="language === 'zh'" :span="16">
        <el-row>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">公司产品</h6>
            <ul class="list-unstyled">
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/8.html"
                target="_blank"
                style="cursor:pointer"
              >扫码支付终端</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/9.html"
                target="_blank"
                style="cursor:pointer"
              >智能收银机</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/6.html"
                target="_blank"
                style="cursor:pointer"
              >智能多媒体POS</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/2.html"
                target="_blank"
                style="cursor:pointer"
              >台式POS</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/5.html"
                target="_blank"
                style="cursor:pointer"
              >mPOS</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/1.html"
                target="_blank"
                style="cursor:pointer"
              >手持无线POS</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/pros/pros/type_id/7.html"
                target="_blank"
                style="cursor:pointer"
              >外接设备</a></li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">服务中心</h6>
            <ul class="list-unstyled">
              <li><a
                href="http://old.newlandpayment.com/cn/home/sup/after.html"
                target="_blank"
                style="cursor:pointer"
              >服务政策</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/sup/qus.html"
                target="_blank"
                style="cursor:pointer"
              >常见问题</a></li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/sup/network.html"
                target="_blank"
                style="cursor:pointer"
              >服务网点</a>
              </li>
              <li><a
                href="http://old.newlandpayment.com/cn/home/sup/download.html"
                target="_blank"
                style="cursor:pointer"
              >下载中心</a>
              </li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">加入我们</h6>
            <ul class="list-unstyled">
              <li><a href="http://old.newlandpayment.com/cn/home/career/coll_career.html" target="_blank">校园招聘</a></li>
              <li><a href="http://old.newlandpayment.com/cn/home/career/soc_career.html" target="_blank">社会招聘</a></li>
              <li><a href="http://old.newlandpayment.com/cn/home/team/team.html" target="_blank">社团风采</a></li>
              <li><a href="http://old.newlandpayment.com/cn/home/office/office.html" target="_blank">办公环境</a></li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">联系我们</h6>
            <ul class="list-unstyled link-mode">
              <li><a
                href="http://old.newlandpayment.com/cn/home/contact/inquiry.html"
                target="_blank"
                style="cursor:pointer"
              >留言</a></li>
              <li><a href="http://old.newlandpayment.com/cn/home/contact/contact.html" target="_blank">联系信息</a></li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
      <el-col v-if="language !== 'zh'" :span="16">
        <el-row>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">Services</h6>
            <ul class="list-unstyled">
              <li><a
                href="https://www.newlandpayment.com/show-list-12725-1.html"
                target="_blank"
                style="cursor:pointer"
              >Payment Ecosystem</a></li>
              <li><a
                href="https://www.newlandpayment.com/show-list-12771-1.html"
                target="_blank"
                style="cursor:pointer"
              >Security Tools</a></li>
              <li><a
                href="https://www.newlandpayment.com/show-list-12769-1.html"
                target="_blank"
                style="cursor:pointer"
              >SDK Toolkit</a></li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">Solutions</h6>
            <ul class="list-unstyled">
              <li><a
                href="https://www.newlandpayment.com/news-list-7769-1.html"
                target="_blank"
                style="cursor:pointer"
              >mPOS</a></li>
              <li><a
                href="https://www.newlandpayment.com/news-list-7771-1.html"
                target="_blank"
                style="cursor:pointer"
              >Android POS</a></li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">Support</h6>
            <ul class="list-unstyled">
              <li><a href="https://www.newlandpayment.com/news-list-7763-1.html#jishu" target="_blank">NPT Support Center</a></li>
              <li><a href="https://www.newlandpayment.com/news-list-7763-1.html#ruanjian" target="_blank">Software Training</a></li>
              <li><a href="https://www.newlandpayment.com/news-list-7763-1.html#shouhou" target="_blank">After-sales Service</a></li>
            </ul>
          </el-col>
          <el-col :span="6" class="footer-nav">
            <h6 class="heading mb-3">About NPT</h6>
            <ul class="list-unstyled link-mode">
              <li><a
                href="https://www.newlandpayment.com/news-list-7763-1.html#shouhou"
                target="_blank"
                style="cursor:pointer"
              >Company Overview</a></li>
              <li><a href="https://www.newlandpayment.com/about-list-2565-1.html" target="_blank">Newsroom</a></li>
              <li><a href="https://www.newlandpayment.com/news-list-7767-1.html" target="_blank">Contact Us</a></li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" style="text-align: center">
        <p class="rights text-center" style="font-size: 12px;">© 2020 Newland Payment Technology. All rights reserved.</p>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: 'IndexFooter',
  data() {
    return {
    }
  },
  computed: {
    language() {
        return this.$i18n.locale;
    }
  }
}
</script>

<style scoped lang="scss">
  .index-footer{
      background: #253e6e;

    .heading{
      color: #fff;
      font-size: 1.2rem;
    }
    .group-info{
      color: white;
      list-style: none;
    }

    .list-unstyled{
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .list-unstyled a{
      font-size: 1rem;
      line-height: 32px;
      color: rgba(255, 255, 255, 0.5);
      font-weight: normal;
    }

    .rights{
      color: rgba(255, 255, 255, 0.5);
      margin-top: 80px;
      line-height: 33px;
    }
  }
</style>
