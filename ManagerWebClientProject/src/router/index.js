import Vue from 'vue'
import Router from 'vue-router'

import Main from "../views/Main";
import UserMessage from "../views/userManage/UserMessage";
import Login from "../views/Login";
import ContentMessage from "../views/contentManage/ContentMessage";
import ContentCheck from "../views/contentManage/ContentCheck";
import ContentList from "../views/reportManage/ReportList";
import ElementUI from "element-ui";

Vue.use(Router)
import cookies from 'vue-cookies'

const router = new Router({
  mode: 'history',

  routes: [
    {
      path: '/main',
      name: 'Main',
      meta: {
        title: '后台管理'
      },
      component: Main,
      children: [
        {path: '/userManage/UserMessage', name: 'UserMessage', component: UserMessage},
        {path: '/contentManage/contentMessage', name: 'ContentMessage', component: ContentMessage},
        {path: '/contentManage/contentCheck', name: 'ContentCheck', component: ContentCheck},
        {path: '/reportManage/ContentList', name: 'ContentList', component: ContentList}
      ]
    },
    {
      path: '/goHome',
      redirect: '/main'
    },
    {
      path: '/',
      name: 'Login',
      component: Login
    },
  ]
})

router.beforeEach((to, from, next) => {
  localStorage.effectiveTime = 10;
  let token = localStorage.getItem('token');
  console.log("index: " + token);
  if (to.name !== 'Login' && token == null) {
    ElementUI.Message("请先登录！");
    next({name: 'Login'})
  }
  else next()
})
export default router
