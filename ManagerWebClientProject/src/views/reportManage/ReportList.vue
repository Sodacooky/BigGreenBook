<template>
  <div>
    <el-row>
      <el-col :span="1.5" :offset="0.5">
        <el-badge :value="totalReports" class="item">
          <el-button size="small" @click="change1">未处理举报</el-button>
        </el-badge>
      </el-col>
      <el-col :span="1.5" :offset="1">
        <el-badge class="item">
          <el-button size="small" @click="change2">已忽略举报</el-button>
        </el-badge>
      </el-col>
    </el-row>
    <br/>

    <el-row  v-if="show === 0">
      <el-row>
        <el-col :span="8" v-for="(item, index) in reportList" :key="index" :offset="0" style="width: 419px; height: 140px">
          <el-card v-if="item.solved === 0" :body-style="{ padding: '0px' } ">
            <div style="padding: 14px;">

              <el-row>
              <el-col :span="1.5">
                <div>
                <span style="padding: 0; font-size: 14px">内容：
                  <el-link :underline="false" type="primary" @click="gotoContent(item.cid)">{{item.title}}</el-link>
                  被举报</span>
                </div>
              </el-col>
              </el-row>
              <br/>

              <el-row>
              <el-col :span="1.5">
                <div style="color: #909399">
                  <el-divider style="width: 10px" direction="vertical"></el-divider>
                  <span style="font-size: 13px">原因： {{item.reason}} </span>
                </div>
              </el-col>
              </el-row>

            <br/>
              <el-row>
              <el-col :span="1.5">
              <div style="font-size: 13px; color: #909399"><i class="el-icon-time"></i>
              <span>{{item.date}}</span>
              </div>
              </el-col>
                <el-button type="text" class="button" @click="openIgnore(item.uid, item.cid)">忽略</el-button>
                <el-button type="text" class="button" @click="openDeleteSelect(item.cid)">删除内容</el-button>
              </el-row>
            </div>
          </el-card>

        </el-col>
      </el-row>
      <br/>
      <el-pagination @current-change="handleCurrentChange"
                     background
                     :current-page="currentPage"
                     layout="prev, pager, next"
                     :total="totalPage">
      </el-pagination>
    </el-row>


    <el-row  v-if="show === 1">
      <el-row>
        <el-col :span="8" v-for="(item, index) in reportList" :key="index" :offset="0" style="width: 419px; height: 140px">
          <el-card v-if="item.solved === 1" :body-style="{ padding: '0px' } ">
            <div style="padding: 14px;">

              <el-row>
                <el-col :span="1.5">
                  <div>
                <span style="padding: 0; font-size: 14px">内容：
                  <el-link :underline="false" type="primary" @click="gotoContent(item.cid)">{{item.title}}</el-link>
                  被举报</span>
                  </div>
                </el-col>
              </el-row>
              <br/>

              <el-row>
                <el-col :span="1.5">
                  <div style="color: #909399">
                    <el-divider style="width: 10px" direction="vertical"></el-divider>
                    <span style="font-size: 13px">原因： {{item.reason}} </span>
                  </div>
                </el-col>
              </el-row>

              <br/>
              <el-row>
                <el-col :span="1.5">
                  <div style="font-size: 13px; color: #909399"><i class="el-icon-time"></i>
                    <span>{{item.date}}</span>
                  </div>
                </el-col>
                <el-button type="text" class="button" disabled>已忽略</el-button>
                <el-button type="text" class="button" @click="openDeleteSelect(item.cid)">删除内容</el-button>
              </el-row>
            </div>
          </el-card>

        </el-col>
      </el-row>
      <br/>
      <el-pagination @current-change="handleCurrentChange"
                     background
                     :current-page="currentPage"
                     layout="prev, pager, next"
                     :total="totalPage">
      </el-pagination>
    </el-row>

  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";

export default {
  name: "ContentList",
  data() {
    return {
      show: 0,
      currentPage: 1,
      pageSize: 12,
      totalPage: 1,
      reportList: '',
      totalReports: ''
    }
  },
  methods: {
    loadPage: function (currentPage) {
      let _this = this;
      axios({
        method: 'get',
        url: 'http://localhost:8080/manage/getReports/' + currentPage,
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        _this.totalPage = Math.ceil(obj.totalReports / _this.pageSize)*10;
        _this.totalReports = obj.totalReports;
        for (let i = 0; i < obj.list.length; i++) {
          obj.list[i].date = _this.$moment(obj.list[i].date).format('YYYY-MM-DD HH:mm:ss');
          console.log(obj.list[i].solved);
        }
        _this.reportList = obj.list;
      });
    },

    handleCurrentChange: function(currentPage){
      let _this = this;
      _this.loadPage(currentPage);
      console.log(this.currentPage)  //点击第几页
    },

    deleteContent(cid) {
      let _this = this;
      let select = [];
      select.push(cid);
      axios({
        method: 'get',
        url: 'http://localhost:8080/manage/deleteSelect/' + select,
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        _this.handleCurrentChange(_this.currentPage);
      })
    },

    openDeleteSelect(cid) {
      this.$confirm('是否删除内容？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.deleteContent(cid);
      })
    },

    gotoContent: function (cid) {
      let _this= this;
      axios({
        method: 'get',
        url: 'http://localhost:8080/manage/check/' + cid,
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        let content = res.data;
        console.log("goto: " + content.date);
        _this.$router.push({path: '/contentManage/ContentCheck/', query: {content: content}});
      })
    },

    ignoreReport: function (uid, cid) {
      let _this = this;
      axios({
        method: 'get',
        url: 'http://localhost:8080/manage/ignore/' + uid + '/' + cid,
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        _this.handleCurrentChange(_this.currentPage);
      })
    },

    openIgnore: function (uid, cid) {
      this.$confirm('是否忽略该条举报？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '该举报已被忽略!'
        });
        console.log("uid: " + uid);
        this.ignoreReport(uid, cid);
      })
    },

    change1: function () {
      let _this = this;
      if (_this.show === 1) {
        _this.loadPage(1);
      }
      _this.show = 0;

    },
    change2: function () {
      let _this = this;
      if (_this.show === 0) {
        _this.loadIgnoreReports(1);
      }
      _this.show = 1;
    },

    loadIgnoreReports: function (currentPage) {
      let _this = this;
      axios({
        method: 'get',
        url: 'http://localhost:8080/manage/getIgnore/' + currentPage,
        contentType:"application/json;charset=UTF-8",
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        _this.totalPage = Math.ceil(obj.totalReports / _this.pageSize)*10;
        _this.totalReports = obj.totalReports;
        for (let i = 0; i < obj.list.length; i++) {
          obj.list[i].date = _this.$moment(obj.list[i].date).format('YYYY-MM-DD HH:mm:ss');
          console.log(obj.list[i].solved);
        }
        _this.reportList = obj.list;
      });
    }


  },

  mounted() {
    this.handleCurrentChange(this.currentPage);
  }
}
</script>

<style scoped>

</style>
