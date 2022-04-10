import Vue from 'vue'
import Router from 'vue-router'

import Main from "../views/Main";
import UserMessage from "../views/userManage/UserMessage";
import Login from "../views/Login";
import ContentMessage from "../views/contentManage/ContentMessage";
import ContentCheck from "../views/contentManage/ContentCheck";
import ImageShow from "../views/contentManage/ImageShow";

Vue.use(Router)

export default new Router({
  mode: 'history',

  routes: [
    {
      path: '/',
      name: 'Main',
      meta: {
        title: '后台管理'
      },
      component: Main,
      children: [
        {path: '/userManage/UserMessage', name: 'UserMessage', component: UserMessage},
        {path: '/contentManage/contentMessage', name: 'ContentMessage', component: ContentMessage},
        {path: '/contentManage/contentCheck', name: 'ContentCheck', component: ContentCheck}
      ]
    },
    {
      path: '/goHome',
      redirect: '/'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {path: '/contentManage/contentCheck',
      name: 'ContentCheck',
      component: ContentCheck,
      children: [
        {path: '/contentManage/ImageShow', name: ImageShow, component: ImageShow}
      ]
    }

  ]
})
