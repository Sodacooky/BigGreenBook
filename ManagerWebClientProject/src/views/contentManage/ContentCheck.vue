<template>
  <div>
    <el-header style="height: 80px">
      <el-page-header @back="goBack">
      </el-page-header>
      <el-col :span="1.5" :offset="8">
      <el-input size="medium" style="width: 180px" v-model="input" placeholder="请输入cid"></el-input>
      <el-button type="primary" size="small" @click="queryContent(input)" icon="el-icon-search" >搜索</el-button>
      </el-col>

      <el-col :span="1.5" :offset="2" style="box-shadow: 0 0 0, 0 0 0;">
      <el-button type="primary" size="small" @click="previousContent">上一篇</el-button>
      <el-button type="primary" size="small" @click="nextContent">下一篇</el-button>
      </el-col>
      <el-button type="warning" size="small" icon="el-icon-warning-outline" @click="openSuspend(content.uid, content.author)">封禁用户</el-button>
      <el-button type="danger" size="small" icon="el-icon-delete" @click="openDelete(content.cid, content.text)">删除</el-button>

      <div>
      <br/>
      </div>
    </el-header>
    <el-main>
    <!-- 内容 -->
      <el-row>
        <el-col :span="1.5" :offset="2" style=" padding: 0; padding-right: 6px">
          <div>
            <el-tag>发布时间</el-tag>
            {{content.date}}
          </div>
        </el-col>

        <el-col :span="1.5" :offset="4" style=" padding: 0; padding-right: 6px">
          <div>
          <el-tag>作者</el-tag>
          {{content.author}}
          </div>
        </el-col>

        <el-col :span="1.5" :offset="1" style=" padding: 0; padding-right: 6px">
          <div style="line-height: 6px">
            <el-tag>标题</el-tag>
            {{content.title}}
          </div>
        </el-col>

        <el-col :span="1.5" :offset="3" style=" padding: 0; padding-right: 4px">
          <div>
            <el-tag style="height: 30px" type="danger">点赞数</el-tag>
            {{content.likeAmount}}
          </div>
        </el-col>
      </el-row>

      <el-row>
        <el-row>
          <el-col v-if="content.type === 'picture'" :span="0.8" :offset="1" style="height: 12px"><el-tag type="info">图片</el-tag></el-col>
          <el-col v-if="content.type === 'video'" :span="0.8" :offset="1" style="height: 12px"><el-tag type="info">视频</el-tag></el-col>
          <el-col :span="0.8" :offset="12" style="height: 12px"><el-tag>正文</el-tag></el-col>
        </el-row>

        <el-col :span="12" :offset="1" style="width: 612px">
          <br/>
          <div v-if="content.type === 'picture'" class="grid-content bg-purple">
            <div class="block" v-model="content">
              <el-carousel trigger="click" arrow="always" indicator-position="outside"
                           :interval="2000" style="width:592px" height="335px">
                <el-carousel-item v-for="item in content.paths" :key="item">
                  <el-image :src="'https://sodacooky.plus:8080/static/'+ item" fit="cover" :preview-src-list="srcList"></el-image>
                </el-carousel-item>
              </el-carousel>
            </div>
          </div>
          <br/>

          <!--视频-->
          <div v-if="content.type === 'video'" class="grid-content bg-purple">
            <div class="block">
              <video width="592" height="335" controls>
                <source :src="'/api/' + content.paths[0]" type="video/mp4">
              </video>
            </div>
          </div>

        </el-col>
        <el-col :span="9" :offset="1"><br/><div class="grid-content bg-purple-light">{{content.text}}</div>
          <br/>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";

export default {
  name: "ContentCheck",
  data() {
    return {
      input: '',
      content: '',
      contentList: '',
      bannerHeight :1000,
      srcList: [],
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },

    openDelete(cid, title) {
      this.$confirm('是否删除标题为 ' + title + ' 的文章?', '提示', {
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

    deleteContent: function (cid) {
      let _this = this;
      let select = [];
      select.push(cid);
      axios({
        method: 'get',
        url: '/product/manage/deleteSelect/' + select,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        _this.goBack();
      })
    },

    queryContent: function (cid) {
      let _this= this;
      axios({
        method: 'get',
        url: '/product/manage/check/' + cid,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        console.log(res.data);
        if (res.data !== '') {
          _this.content = res.data;
        }
        else {
          _this.$message("内容不存在！");
        }
      })
    },

    nextContent: function (cid) {
      let _this = this;
      console.log("cid: " + _this.content.cid);
      axios({
        method: 'get',
        url: '/product/manage/nextContent/' + _this.content.cid + '/' + _this.content.date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        if (list[0] !== undefined) {
          _this.contentList = list;
          _this.content = list[0];
          _this.cookie.set('content', _this.content);
          for (let i = 0; i < _this.content.paths.length; i++) {
            _this.srcList[i] = 'https://sodacooky.plus:8080/static/' + _this.content.paths[i];
          }
          _this.content.date = _this.$moment(_this.content.date).format('YYYY-MM-DD HH:mm:ss');
          console.log("date: " + list[0].date);
        }
        else {
          _this.$message("已经是最后一个了！");
        }
      })
    },

    previousContent: function (cid) {
      let _this = this;
      console.log("cid: " + _this.content.cid);
      axios({
        method: 'get',
        url: '/product/manage/previousContent/' + _this.content.cid + '/' + _this.content.date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        if (list[0] !== undefined) {
          _this.contentList = list;
          _this.content = list[0];
          _this.cookie.set('content', _this.content);
          for (let i = 0; i < _this.content.paths.length; i++) {
            _this.srcList[i] = 'https://sodacooky.plus:8080/static/' + _this.content.paths[i];
          }
          _this.content.date = _this.$moment(_this.content.date).format('YYYY-MM-DD HH:mm:ss');
          console.log("date: " + list[0].date);
        }
        else {
          _this.$message("已经是第一个了！");
        }
      })
    },

    openSuspend(uid, nickname) {
      this.$confirm('是否封禁用户 ' + nickname + ' ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '用户 ' + nickname +' 已被封禁!'
        });
        this.suspendUser(uid);
      })
    },

    suspendUser: function (uid) {
      let _this = this;
      _this.inputId = uid;
      axios({
        method: 'get',
        url: '/product/manage/suspend/' + uid,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
      })
    }

  },

  created() {
    let _this = this;
    this.content = this.$route.query.content;
    for (let i = 0; i < _this.content.paths.length; i++) {
      _this.srcList[i] = 'https://sodacooky.plus:8080/static/' + _this.content.paths[i];
      _this.content.date = _this.$moment(_this.content.date).format('YYYY-MM-DD HH:mm:ss')
    }
    _this.cookie.set('content', _this.content);
    console.log("storage: " + this.localStorage.getItem('content'))
  },

  mounted() {
    this.content = this.cookie.get('content');
  }

}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-main {
  background-color: #FFFFFF;
}


.el-row {
  margin-bottom: 20px;
  /*background-color: #f9fafc;*/

}
.el-col {
  border-radius: 4px;
  background-color: #f9fafc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: rgb(249,250,252);
}
.bg-purple-light {
  background: rgb(249,250,252);
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.el-main {
  background-color: #909399;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.el-main{
  background-color: rgb(241, 242, 246);
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}


img {
  max-width: 100%;
  max-height: 100%;
}

</style>
