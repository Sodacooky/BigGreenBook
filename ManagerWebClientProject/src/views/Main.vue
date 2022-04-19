<template>
  <div>
    <el-container>
      <el-aside width="220px">
        <el-menu background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#ffd04b"
                  :default-active="$route.path"
                  router>

          <el-menu default-active="1-4-1" class="el-menu-vertical-demo" background-color="#545c64"
                   text-color="#fff"
                   active-text-color="#ffd04b" :default-active="$route.path" router>
            <el-menu-item index="/goHome">
              <i class="el-icon-house"></i>
              <router-link to="/goHome"></router-link>首页
            </el-menu-item>
          </el-menu>

          <el-submenu index="1">
            <template slot="title"><i class="el-icon-user-solid"></i>用户管理</template>
            <el-menu-item-group>
              <el-menu-item index="/userManage/UserMessage">
                <!--插入的地方-->
                用户列表
              </el-menu-item>

            </el-menu-item-group>
          </el-submenu>


          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>内容管理</template>
            <el-menu-item-group>
              <el-menu-item index="/contentManage/contentMessage">
                <!--插入的地方-->
                <router-link to="/contentManage/contentMessage"></router-link>内容列表
              </el-menu-item>

            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="3" style="height: 600px">
            <template slot="title"><i class="el-icon-message-solid"></i>举报处理</template>
            <el-menu-item-group>
              <el-menu-item index="/reportManage/ContentList">
                <!--插入的地方-->
                <router-link to="/reportManage/ContentList"></router-link>消息列表
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!--          <span>{{name}}</span>-->
        </el-header>
        <el-main>
          <!--在这里展示视图-->
          <router-view/>
        </el-main>
      </el-container>

    </el-container>

  </div>
</template>

<script>
import UserMessage from "./userManage/UserMessage";
export default {
  name: "Main",
  methods: {
    flushCom: function () {
      this.$router.go(0);
    },
    exit: function () {
      window.localStorage.clear();
      window.location.reload()
    }
  },
  mounted() {
    document.title="后台管理"
  }
}
</script>

<style scoped lang="scss">
.el-header {
  background-color: #B3C0D2;
  color: #333;
  line-height: 60px;
}
.el-aside {
  background-color: #545c64;
  color: #333;
}
.el-menu-item.is-active {
  background-color: #247bb4 !important;
}

.el-menu {
  border-right: 0;
}
.left-menu{
  height: 100vh;
}


body .el-table th.gutter{
  display: table-cell!important;
}
//.el-menu-item{
//  background-color: rgb(40,57,74);
//  color: white;
//}
//.el-submenu /deep/ .el-submenu__title{
//  background-color: rgb(40,57,74);
//  color: white;
//}
//.el-aside{
//  background-color: rgb(40,57,74);
//}
//.el-submenu /deep/ .el-menu-item-group__title{
//  background-color: rgb(40,57,74);
//}
//.el-aside{
//  background-color: rgb(40,57,74);
//}
</style>
