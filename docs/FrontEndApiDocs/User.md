# 用户相关

***

## 用户登录

```http request
GET /usr/login
```

参数：

| 参数名  | 说明                     |
|------|------------------------|
| code | 微信小程序wx.login得到的jscode |

返回：

自定义登录记录字符串 (3rdSession)

***

## 获取自定义登录记录字符串(3rdSession)可用性

```http request
GET /usr/checkCustomCodeState
```

参数：

| 参数名        | 说明           |
|------------|--------------|
| customCode | 上一个方法中获得的字符串 |

返回：

是否可用布尔值

***

## 获取用户自己的个人信息

```http request
GET /usr/get_my_info
```

参数：

| 参数名        | 说明             |
|------------|----------------|
| customCode | 登录时获取到的自定义登录记录 |

返回：

用户信息POJO类，属性如下：

| 属性          | 类型      | 说明             |
|-------------|---------|----------------|
| uid         | String  | 用户的微信OpenID    |
| nickname    | String  | 用户的昵称          |
| description | String  | 用户的自我描述        |
| gender      | Integer | 用户的性别，0女，1男    |
| birthday    | Date    | 用户的生日          |
| state       | Integer | 用户的状态，0正常，1被封禁 |
| avatarPath  | String  | 用户的头像的路径       |

***

## 获取指定UID用户的个人信息

```http request
GET /usr/get_info
```

参数：

| 参数名 | 说明     |
|-----|--------|
| uid | 用户的uid |

返回：

用户信息POJO类，属性见上一条。

*** 

## 获取用户的关注者（粉丝）

```http request
GET /usr/get_followers
```

参数：

| 参数名        | 说明           |
|------------|--------------|
| customCode | 用户自定义登录记录字符串 |
| uid        | 要查看的用户的id    |
| page       | 页            |

返回：

用户预览卡片数组，如果超过了最后一页，返回空数组。

预览卡片数组属性如下：

| 属性             | 类型      | 说明             |
|----------------|---------|----------------|
| userUid        | String  | 用户uid          |
| userNickname   | String  | 用户昵称           |
| userAvatarPath | String  | 用户头像URL        |
| contentAmount  | Integer | 发布的内容的数量       |
| fansAmount     | Integer | 关注者（粉丝）数量      |
| status         | Integer | 该用户是(1)否(0)已关注 |

***

## 获取用户的正在关注

```http request
GET /usr/get_followings
```

参数：

| 参数名        | 说明           |
|------------|--------------|
| customCode | 用户自定义登录记录字符串 |
| uid        | 要查看的用户的id    |
| page       | 页            |

返回：

用户预览卡片数组，如果超过了最后一页，返回空数组。

预览卡片数组属性如下：

| 属性             | 类型      | 说明             |
|----------------|---------|----------------|
| userUid        | String  | 用户uid          |
| userNickname   | String  | 用户昵称           |
| userAvatarPath | String  | 用户头像URL        |
| contentAmount  | Integer | 发布的内容的数量       |
| fansAmount     | Integer | 关注者（粉丝）数量      |
| status         | Integer | 该用户是(1)否(0)已关注 |

***

## 获取用户的收藏夹内容

```http request
GET /usr/get_collections
```

参数：

| 参数名  | 说明             |
|------|----------------|
| uid  | 要获取收藏夹的用户的uid  |
| page | 要获取收藏夹的页号，从0开始 |

返回：

如果对方设置了没有公开，或者超过了最后一页，返回空数组。

PreviewCard数组，PreviewCard类型如下：

| 属性                | 类型      | 说明                       |
|-------------------|---------|--------------------------|
| contentCid        | String  | 内容的ID                    |
| resourceType      | String  | 资源的类型，为"picture"或"video" |
| resourcePath      | String  | 用于卡片显示的资源URL，为图片或视频的URL  |
| contentTitle      | String  | 内容的标题                    |
| userAvatarPath    | String  | 该内容作者的头像的图片的URL          |
| userNickname      | String  | 该内容作者的昵称                 |
| contentLikeAmount | Integer | 该内容的点赞数量                 |

***

## 获取用户【自己】的收藏夹

```http request
get_my_collections
```

相比上一条，因为是获取**自己**的，所以不会受权限影响。

参数：

| 参数名        | 说明             |
|------------|----------------|
| customCode | 用户自定义登录记录字符串   |
| page       | 要获取收藏夹的页号，从0开始 |

返回：

如果对方设置了没有公开，或者超过了最后一页，返回空数组。

PreviewCard数组，PreviewCard类型如下：

| 属性                | 类型      | 说明                       |
|-------------------|---------|--------------------------|
| contentCid        | String  | 内容的ID                    |
| resourceType      | String  | 资源的类型，为"picture"或"video" |
| resourcePath      | String  | 用于卡片显示的资源URL，为图片或视频的URL  |
| contentTitle      | String  | 内容的标题                    |
| userAvatarPath    | String  | 该内容作者的头像的图片的URL          |
| userNickname      | String  | 该内容作者的昵称                 |
| contentLikeAmount | Integer | 该内容的点赞数量                 |

***

## 获取用户的收藏夹页数

```http request
GET /usr/get_collections_page_amount
```

参数：

| 参数名  | 说明             |
|------|----------------|
| uid  | 要获取收藏夹的用户的uid  |

返回：

整数类型的页数。

当为0时，没有收藏内容。

***

## 关注用户

```http request
GET /usr/follow
```

参数：

| 参数名        | 说明                   |
|------------|----------------------|
| customCode | 发起人（用户自己）的自定义登录记录字符串 |
| goal_uid   | 要关注的认的uid            | 

返回：

动作是否成功布尔值

***

## 取消关注用户

```http request
GET /usr/unfollow
```

参数：

| 参数名        | 说明                   |
|------------|----------------------|
| customCode | 发起人（用户自己）的自定义登录记录字符串 |
| goal_uid   | 要取消关注的认的uid          | 

返回：

动作是否成功布尔值

***

## 获取关注状态

```http request
GET /usr/get_follow_state
```

参数：

| 参数名        | 说明                   |
|------------|----------------------|
| customCode | 发起人（用户自己）的自定义登录记录字符串 |
| goal_uid   | 目标用户的uid             | 

返回：

是否已经关注布尔值


***

## 更新用户基本信息

```http request
POST /usr/updateUser
```

参数：

用户JSON对象：

| 属性             | 类型      | 说明             |
|----------------|---------|----------------|
| nickname       | String  | 用户的昵称          |
| description    | String  | 用户的自我描述        |
| gender         | Integer | 用户的性别，0女，1男    |
| birthday       | Date    | 用户的生日          |

*USER POJO类的其他属性保持为NULL*

***
