<template>
  <div>
    <el-page-header @back="goBack">
    </el-page-header>

      <el-select size="medium" v-model="value" placeholder="请选择" clearable>
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

    <el-input size="medium" v-if="value === '选项1'" style="width: 180px" v-model="inputId" @keyup.enter.native="query(inputId)" placeholder="请输入uid"></el-input>
    <el-button type="primary" v-if="value === '选项1'" size="small" @click="query(inputId)" icon="el-icon-search">搜索uid</el-button>
    <el-input size="medium" v-if="value === '选项2' || value === ''" style="width: 180px" v-model="input" @keyup.enter.native="queryNickname(input)" placeholder="请输入昵称"></el-input>
    <el-button type="primary" v-if="value === '选项2' || value === ''"  size="small" @click="queryNickname(input)" icon="el-icon-search">搜索</el-button>

    <br></br>

    <el-table
      :data="tableData"
      style="width: 100%" >
      <el-table-column v-model="tableData.uid"
        prop="uid"
        label="ID"
        width="120">
      </el-table-column>

      <el-table-column
        prop="img"
        label="头像"
        width=70>
        <template v-slot="scope">
          <div class="demo-fit" id="avatar">
            <div><el-avatar shape="square"  :size="50" :fit="true" :src="scope.row.avatar_path"></el-avatar></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column v-slot="scope">
        <el-button type="primary" size="small"  @click="openReset(scope.row.uid, scope.row.nickname)">重置</el-button>
      </el-table-column>

      <el-table-column
        prop="nickname"
        label="昵称"
        width="130">
      </el-table-column>
      <el-table-column v-slot="scope">
        <el-button type="primary" size="small" icon="el-icon-edit" @click="openNickName(scope.row.uid)">修改</el-button>
      </el-table-column>

      <el-table-column
        prop="description"
        label="个性签名"
        width="200">
      </el-table-column>
      <el-table-column v-slot="scope">
        <el-button type="primary" size="small" icon="el-icon-edit" @click="openDescription(scope.row.uid)">修改</el-button>
      </el-table-column>

      <el-table-column v-slot="scope"
        prop="state"
        label="封禁状态"
        width="80">
        <el-tag v-if="scope.row.state === '正常'">正常</el-tag>
        <el-tag type="danger" v-if="scope.row.state === '封禁中'">封禁中</el-tag>
      </el-table-column>
      <el-table-column v-slot="scope" width="80">

        <el-button v-if="scope.row.state === '正常'" type="danger" size="small"  @click="openSuspend(scope.row.uid, scope.row.nickname)">封禁</el-button>
        <el-button v-if="scope.row.state === '封禁中'" type="danger" size="small" disabled="disabled">封禁</el-button>
      </el-table-column>
      <el-table-column v-slot="scope">
        <el-button v-if="scope.row.state === '封禁中'" type="warning" size="small" @click="openRestore(scope.row.uid, scope.row.nickname)">解封</el-button>
        <el-button v-if="scope.row.state === '正常'" type="warning" size="small" disabled="disabled">解封</el-button>
      </el-table-column>
    </el-table>
    <br></br>

    <el-pagination @current-change="handleCurrentChange"
                   background
                   :current-page="currentPage"
                   layout="prev, pager, next"
                   :total="totalPage">
    </el-pagination>

  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import Router from "vue-router";

export default {
  name: "UserMessage",
  inject: ['reload'],
  data () {
    return {
      count: 0,
      currentPage: 1,
      pageSize: 8,
      totalPage: 1,
      inputId:'',
      input: '',
      tableData: [{
        uid: '',
        nickname: '',
        description: '',
        avatar_path: '',
        state: ''
      }],
      options: [{
        value: '选项1',
        label: '搜uid'
      }, {
        value: '选项2',
        label: '搜昵称'
      }],
      value:''
    }
  },

  methods: {
    goBack() {
      this.$router.go(0);
    },

    openDescription(uid) {
      this.$prompt('修改个性签名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        size: 100
      }).then(({value}) => {
        this.modifyDescription(uid, value);
        this.$message({
          type: 'success',
          message: '修改成功！'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },

    openNickName(uid) {
      this.$prompt('修改昵称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        size: 100
      }).then(({value}) => {
        this.modifyNickName(uid, value);
        this.$message({
          type: 'success',
          message: '修改成功！'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },

    query: function (uid) {
      var _this = this;
      if (uid === '') {
        _this.currentPage = 1;
       _this.loadPage(_this.currentPage);
      }
      _this.tableData = [];
      axios({
        method: 'get',
        url: '/product/manage/query/' + uid,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
          let obj = JSON.parse(JSON.stringify(res.data));
          console.log(res.data)
          let state = "正常";
          if (obj.state === 1) {
            state = "封禁中";
          }
          console.log(_this.tableData);
          _this.totalPage = 1;
          Vue.set(_this.tableData, 0, {
            uid: obj.uid, nickname: obj.nickname,
            description: obj.description, avatar_path: "https://sodacooky.plus:8080/static/" + obj.avatarPath, state: state
          });
      })
    },

    queryNickname: function (input) {
      var _this = this;
      if (input === '') {
        _this.currentPage = 1;
        _this.loadPage(_this.currentPage);
      }
      _this.tableData = [];
      axios({
        method: 'get',
        url: '/product/manage/queryName/'+ _this.currentPage + '/' + input,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        _this.totalPage = Math.ceil(obj.totalUsers / _this.pageSize)*10;
        console.log(list);
        for (let i = 0; i < list.length; i++) {
          let state = "正常";
          if (list[i].state === 1) {
            state = "封禁中";
          }
          console.log(state);
          Vue.set(_this.tableData, i, {
            uid: list[i].uid, nickname: list[i].nickname,
            description: list[i].description, avatar_path: "https://sodacooky.plus:8080/static/" + list[i].avatarPath, state: state});
        }
      })

    },

    resetAvatar(uid) {
      var _this = this;
      _this.inputId = uid;

      axios({
        method: 'get',
        url: '/product/manage/reset/' + uid,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let state = "正常";
        if (obj.state === 0) {
          state = "封禁中";
        }
        Vue.set(_this.tableData, 0, {
          uid: obj.uid, nickname: obj.nickname,
          description: obj.description, avatar_path: obj.avatarPath, state: state});

          _this.query(obj.uid);
          _this.inputId = '';
          _this.reload();
      })
    },

    openReset(uid, nickname) {
      var _this = this;
      this.$confirm('是否重置用户 ' + nickname + ' 的头像?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
          this.$message({
          type: 'success',
          message: '重置成功!'
        });
        this.resetAvatar(uid);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消修改'
        });
      });
    },

    loadAllUser() {
      var _this = this;
      _this.tableData = [];
      axios({
        method: 'get',
        url: '/product/manage/allUser',
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        for (let i = 0; i < obj.length; i++) {
          let state = "正常";
          if (obj[i].state === 1) {
            state = "封禁中";
          }
          Vue.set(_this.tableData, i, {
            uid: obj[i].uid, nickname: obj[i].nickname,
            description: obj[i].description, avatar_path: obj[i].avatarPath, state: state});
        }
      })
    },

    modifyDescription: function (uid, value) {
      var _this = this;
      _this.inputId = uid;
      axios({
        method: 'get',
        url: '/product/manage/updateDesc/' + uid + '/' + value,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
          let obj = JSON.parse(JSON.stringify(res.data));
          let state = "正常";
          if (obj.state === 1) {
            state = "封禁中";
          }
          Vue.set(_this.tableData, 0, {
            uid: obj.uid, nickname: obj.nickname,
            description: obj.description, avatar_path: obj.avatarPath, state: state});
          _this.query(obj.uid);

          _this.reload();
      })
    },

    modifyNickName: function (uid, value) {
      var _this = this;
      _this.inputId = uid;
      axios({
        method: 'get',
        url: '/product/manage/updateName/' + uid + '/' + value,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let state = "正常";
        if (obj.state === 1) {
          state = "封禁中";
        }
        Vue.set(_this.tableData, 0, {
          uid: obj.uid, nickname: obj.nickname,
          description: obj.description, avatar_path: obj.avatarPath, state: state});
        _this.query(obj.uid);
        _this.inputId = '';
        _this.reload();
      })
    },

    openSuspend(uid, nickname) {
      this.$confirm('是否封禁用户 ' + nickname + ' ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }).then(() => {
        this.$message({
          type: 'success',
          message: '用户 ' + nickname +' 已被封禁!'
        });
        let reason = "由于信息违规，您的账号已被封禁！"
        this.suspendUser(uid, reason);
      })
    },



    suspendUser: function (uid, reason) {
      var _this = this;
      _this.inputId = uid;
      axios({
        method: 'get',
        url: '/product/manage/suspend/' + uid + '/' + reason,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
          let obj = JSON.parse(JSON.stringify(res.data));

          let state = "正常";
          if (obj.state === 1) {
            state = "封禁中";
          }
          Vue.set(_this.tableData, 0, {
          uid: obj.uid, nickname: obj.nickname,
          description: obj.description, avatar_path: obj.avatarPath, state: state});
          _this.query(obj.uid);
      })
    },

    openRestore(uid, nickname) {
      this.$confirm('是否解除用户 ' + nickname + ' 的封禁?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '用户 ' + nickname + ' 已被解封!'
        });
        let reason = "您的账号已解封，请以后遵守规则使用！";
        this.restoreUser(uid, reason);
      })
    },

    restoreUser: function (uid, reason) {
      var _this = this;
      _this.inputId = uid;
      axios({
        method: 'get',
        url: '/product/manage/restore/' + uid + '/' + reason,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: _this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));

        let state = "正常";
        if (obj.state === 1) {
          state = "封禁中";
        }
        Vue.set(_this.tableData, 0, {
          uid: obj.uid, nickname: obj.nickname,
          description: obj.description, avatar_path: obj.avatarPath, state: state});
        _this.query(obj.uid);
      })
    },

    loadPage: function (currentPage) {
      var _this = this;
      _this.tableData = [];
      axios({
        method: 'get',
        url: '/product/manage/getUsers/' + currentPage,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
          let obj = JSON.parse(JSON.stringify(res.data));
          let list = obj.list;
          _this.totalPage = Math.ceil(obj.totalUsers / _this.pageSize)*10;

          console.log(list);
          for (let i = 0; i < list.length; i++) {
            let state = "正常";
            if (list[i].state === 1) {
              state = "封禁中";
            }
            console.log(state);
            Vue.set(_this.tableData, i, {
              uid: list[i].uid, nickname: list[i].nickname,
              description: list[i].description, avatar_path: "https://sodacooky.plus:8080/static/" + list[i].avatarPath, state: state});
          }
      })
    },

    handleCurrentChange: function(currentPage){
      var _this = this;
      this.currentPage = currentPage;
      _this.loadPage(currentPage);
      console.log(this.currentPage)  //点击第几页
    },

  },
  mounted() {
    // this.loadAllUser();
    this.handleCurrentChange(this.currentPage);

    console.log("usertoken: " + this.cookie.get("token"));
  }
}
</script>

<style scoped>

</style>
