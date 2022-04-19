<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
                >
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script charset="utf-8">
import axios from "axios";
import cookies from 'vue-cookies'
import Vue from "vue";
Vue.prototype.cookie = cookies

export default {
  name: "Login",
  contentType:"application/json;charset=UTF-8",
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      //表单验证，需要在el-form-item 元素中增加prop 属性
      rules: {
        username: [
          {required: true, message: " 账号不可为空", trigger: 'blur'}
        ],
        password: [
          {required: true, message: " 密码不可为空 ", trigger: 'blur'}
        ]
      },
      //对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    onSubmit(formName) {
      var _this = this;
      axios({
        method: 'get',
        url: '/product/manage/manageLogin/' + this.form.username + '/' + this.form.password,
        contentType:"application/json;charset=UTF-8",
        params: {
          username: this.form.username,
          password: this.form.password
        }
      }).then(function (res) {
        console.log("login: " + res.data.state);
        if (res.data.state === "认证通过") {
          _this.$message({
            message: '登录成功！',
            type: 'success'
          });
          _this.cookie.set("token", res.data.token);
          localStorage.setItem("token", res.data.token);
          localStorage.getItem("token");
          _this.$router.push("/main");
        }
        else {
          _this.$message("账号名或密码错误！");
        }
      })

    }
  }
}

</script>

<style lang="scss" scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>

