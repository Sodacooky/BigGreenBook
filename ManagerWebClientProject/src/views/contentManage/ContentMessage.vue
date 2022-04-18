<template>
  <div>
    <el-row>
    <el-col :span="2" >
      <el-button v-if="multipleSelection.length > 0" type="danger"  icon="el-icon-delete" size="small" @click="openDeleteSelect">批量删除</el-button>
      <el-button v-if="multipleSelection.length === 0" type="primary" icon="el-icon-delete" size="small" disabled>批量删除</el-button>
    </el-col>
      <el-col :span="1.5" :offset="3">
          <el-date-picker size="small"
            v-model="dateValue"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>

      <el-select size="medium" v-model="value" placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
        <el-input v-if="value === ''" size="medium" style="width: 180px" v-model="input" @keyup.enter.native="query(value, input)" placeholder="请输入"></el-input>
        <el-input v-if="value === '选项1'" size="medium" style="width: 180px" v-model="input" @keyup.enter.native="query(value, input)" placeholder="请输入uid"></el-input>
        <el-input v-if="value === '选项2'" size="medium" style="width: 180px" v-model="input" @keyup.enter.native="query(value, input)" placeholder="请输入用户昵称"></el-input>
        <el-input v-if="value === '选项3'" size="medium" style="width: 180px" v-model="input" @keyup.enter.native="query(value, input)" placeholder="请输入标题"></el-input>
        <el-button type="primary" size="small" @click="query(value, input)" icon="el-icon-search">搜索</el-button>
      </el-col>
    </el-row>

    <el-row><br/>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange">

        <el-table-column align="center"
           type="selection"
           width="50">
        </el-table-column>
        <el-table-column align="center"
          prop="cover_path"
          label="内容封面"
          row-style="height:200px"
          width="150">
          <template v-slot="scope">
            <div class="demo-fit">
              <el-image shape="square"  fit="fitWidth" :src="scope.row.cover_path"></el-image>
            </div>
          </template>
        </el-table-column>

            <el-table-column align="center"
              prop="title"
              label="标题">
            </el-table-column>

          <el-table-column align="center"
            prop="author"
            label="作者"
            width="120"
            show-overflow-tooltip>
          </el-table-column>

            <el-table-column align="center"
              prop="like_amount"
              label="点赞数"
              width="90"
              sortable
              show-overflow-tooltip>
            </el-table-column>

          <el-table-column align="center"
            prop="date"
            label="发布时间"
            width="300"
            sortable
            show-overflow-tooltip>
          </el-table-column>

          <el-table-column align="center"
            label="操作"
            width="150"
            show-overflow-tooltip>
            <template v-slot="scope">
              <el-button type="warning" size="small" @click="goToContent(scope.row.cid)">审查</el-button>
              <el-button type="danger" size="small" @click="openDelete(scope.row.cid, scope.row.title)">删除</el-button>
            </template>
          </el-table-column>

    </el-table>
    </el-row>

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
import moment from 'moment';
Vue.prototype.$moment = moment;

export default {
  name: "ContentMessage",
  params: ['content'],
  data() {
    return {
      dateValue: '',
      input: '',
      count: 0,
      currentPage: 1,
      pageSize: 8,
      totalPage: 1,
      imgSrc:'',
      options: [{
        value: '选项1',
        label: '用户ID'
      },
        {
          value: '选项2',
          label: '用户昵称'
        },
        {
          value: '选项3',
          label: '内容标题'
        }
      ],
      value: '',
      tableData: [{
        cid: '',
        title: '',
        author: '',
        like_amount: '',
        date: '',
        cover_path: '',
        videoUrl: ''
      }],
      multipleSelection: []
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val);
    },

    findvideocover(url, file, i) {
      let _this = this;
      const video = document.createElement("video") // 也可以自己创建video
      video.crossOrigin="anonymous";
      let src='/api/vid/op.mp4'; // url地址 url跟 视频流是一样的
      video.src = url;
      var canvas = document.createElement('canvas') // 获取 canvas 对象
      const ctx = canvas.getContext('2d'); // 绘制2d
      let img = new Image();
      video.currentTime = 0.1 // 第一帧
      video.oncanplay= function() {
        canvas.width = 600; // 获取视频宽度
        canvas.height = 500; //获取视频高度
        // 利用canvas对象方法绘图
        ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
        // 转换成base64形式
        const imgsrc = canvas.toDataURL ("image/jpeg") // 截取后的视频封面
        Vue.set(_this.tableData, i, {
          cid: file.cid, title: file.title, author: file.author,
          like_amount: file.likeAmount, cover_path: imgsrc, date: _this.$moment(file.date).format('YYYY-MM-DD HH:mm:ss')})
      };

    },

    loadPage: function (currentPage) {
      let _this = this;
      _this.tableData = [];
      let date = _this.dateIsNull(_this.dateValue);
      axios({
        method: 'get',
        url: '/product/manage/getContents/' + JSON.stringify(currentPage) + '/' + date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        _this.totalPage = Math.ceil(obj.totalContents / _this.pageSize)*10;
        console.log(list);
        for (let i = 0; i < list.length; i++) {
          if (list[i].type === 'video') {
            _this.findvideocover("/api/" +list[i].paths[0], list[i], i);
          }
          else {
            Vue.set(_this.tableData, i, {
              cid: list[i].cid, title: list[i].title, author: list[i].author,
              like_amount: list[i].likeAmount, cover_path: "https://sodacooky.plus:8080/static/" + list[i].paths[0],
              date: _this.$moment(list[i].date).format('YYYY-MM-DD HH:mm:ss')});
          }
        }
      });
    },

    handleCurrentChange: function(currentPage){
      var _this = this;
      this.currentPage = currentPage;
      if (_this.value === "选项1" && _this.input !== '') {
        _this.queryUserId(_this.input);
      }
      else if (_this.value === '选项2' && _this.input !== '') {
        _this.queryNickname(_this.input);
      }
      else if (_this.input === '') {
        _this.loadPage(currentPage);
      }
      else if (_this.value === '选项3' || _this.input !== '') {
        _this.queryContent(_this.input);
      }


      console.log(this.currentPage)  //点击第几页
    },

    deleteSelects: function () {
      let _this = this;
      let contents = _this.multipleSelection;
      let select = [];
      if (contents.length !== 0) {
        for (let i = 0; i < contents.length; i++) {
         select.push(contents[i].cid);
        }
      }
      axios({
        method: 'get',
        url: '/product/manage/deleteSelect/' + select,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function () {
        _this.handleCurrentChange(_this.currentPage);

      })
    },

    openDeleteSelect() {
      this.$confirm('是否删除选中的 ' + this.multipleSelection.length + '条 内容?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.deleteSelects();
      })
    },

    queryContent: function (inputName) {
      let _this = this;
      _this.tableData = [];
      let date = _this.dateIsNull(_this.dateValue);
      axios({
        method: 'get',
        url: '/product/manage/queryContents/' + inputName  + '/' + _this.currentPage + '/' + date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        console.log(list)
        _this.totalPage = Math.ceil(obj.totalContents / _this.pageSize)*10;

        for (let i = 0; i < list.length; i++) {
          if (list[i].type === 'video') {
            _this.findvideocover("/api/" +list[i].paths[0], list[i], i);
          }
          else {
            Vue.set(_this.tableData, i, {
              cid: list[i].cid, title: list[i].title, author: list[i].author,
              like_amount: list[i].likeAmount, cover_path: "https://sodacooky.plus:8080/static/" + list[i].paths[0],
              date: _this.$moment(list[i].date).format('YYYY-MM-DD HH:mm:ss')});
          }
        }
      })
    },

    queryUserId: function (uid) {
      let _this = this;
      console.log(_this.dateValue);
      _this.tableData = [];
      let date = _this.dateIsNull(_this.dateValue);
      axios({
        method: 'get',
        url: '/product/manage/queryUid/' + uid  + '/' + _this.currentPage + '/' + date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        console.log(list)
        _this.totalPage = Math.ceil(obj.totalContents / _this.pageSize)*10;

        for (let i = 0; i < list.length; i++) {
          if (list[i].type === 'video') {
            _this.findvideocover("/api/" +list[i].paths[0], list[i], i);
          }
          else {
            Vue.set(_this.tableData, i, {
              cid: list[i].cid, title: list[i].title, author: list[i].author,
              like_amount: list[i].likeAmount, cover_path: "https://sodacooky.plus:8080/static/" + list[i].paths[0],
              date: _this.$moment(list[i].date).format('YYYY-MM-DD HH:mm:ss')});
          }
        }
      })
    },

    queryNickname: function (nickname) {
      let _this = this;
      _this.tableData = [];
      let date = _this.dateIsNull(_this.dateValue);
      axios({
        method: 'get',
        url: '/product/manage/queryNickname/' + nickname  + '/' + _this.currentPage + '/' + date,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let obj = JSON.parse(JSON.stringify(res.data));
        let list = obj.list;
        console.log(list)
        _this.totalPage = Math.ceil(obj.totalContents / _this.pageSize)*10;

        for (let i = 0; i < list.length; i++) {
          if (list[i].type === 'video') {
            _this.findvideocover("/api/" +list[i].paths[0], list[i], i);
          }
          else {
            Vue.set(_this.tableData, i, {
              cid: list[i].cid, title: list[i].title, author: list[i].author,
              like_amount: list[i].likeAmount, cover_path: "https://sodacooky.plus:8080/static/" + list[i].paths[0],
              date: _this.$moment(list[i].date).format('YYYY-MM-DD HH:mm:ss')});
          }
        }
      })
    },

    query: function (value, input) {
      let _this= this;
      _this.currentPage = 1;
      if (_this.input === '') {
        _this.loadPage(_this.currentPage);
      }
      else if (_this.value === "选项1") {
        console.log("执行了选项1");
        _this.queryUserId(input);
      }
      else if (_this.value === "选项2") {
        console.log("执行了选项2");
        _this.queryNickname(input);
      }
      else if (_this.value === "选项3" || input !== ''){
        console.log("执行了选项3");
        _this.queryContent(input);
      }

    },

    openDelete(cid, title) {
      this.$confirm('是否删除标题为 ' + title + ' 的内容?', '提示', {
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

    deleteContent(cid) {
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
          _this.handleCurrentChange(_this.currentPage);
      })
    },

    goToContent: function (cid) {
      let _this= this;
      axios({
        method: 'get',
        url: '/product/manage/check/' + cid,
        contentType:"application/json;charset=UTF-8",
        headers: { // 设置请求头
          token: this.cookie.get("token")
        }
      }).then(function (res) {
        let content = res.data;
        console.log("goto: " + content.date);
        _this.cookie.set('content', content);
        _this.$router.push({path: '/contentManage/ContentCheck/', query: {content: content}});
      })
    },

    dateIsNull: function (dateValue) {
      if (dateValue === '' || dateValue === null) {
        dateValue = ['Fri Apr 01 2022 00:00:00 GMT+0800,Sun May 01 2050 00:00:00 GMT+0800']
      }
      console.log("dateValue: " + dateValue);
      return dateValue;
    }

  },

  mounted() {
    this.handleCurrentChange(this.currentPage);
  }
}
</script>

<style scoped>
body .el-table th.gutter{
  display: table-cell!important;

}

el.el-table {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
}

</style>
