前端接口说明文档
- - -

# 首页

## 获取首页瀑布流卡片

```http request
GET /home/get
```

参数：

| 参数名      | 说明                                                              |
|----------|-----------------------------------------------------------------|
| page     | 获取的页数                                                           |
| query_id | 当前时刻(或这一次)进行获取检索使用的ID，用于保证返回的数据不出现，使用```/home/get_query_id```获取 |

返回：

PreviewCard数组，PreviewCard类型如下： |属性|类型|说明| |---|---|---| |contentCid|String|内容的ID| |resourceType|String|资源的类型，为"picture"
或"video"| |resourcePath|String|用于卡片显示的资源URL，为图片或视频的URL| |contentTitle|String|内容的标题|
|userAvatarPath|String|该内容作者的头像的图片的URL| |userNickname|String|该内容作者的昵称| |contentLikeAmount|Integer|该内容的点赞数量|

## 获得首页瀑布流卡片获取检索ID

```http request
GET /home/get_query_id
```

返回：

整数类型的检索ID

## 获得首页瀑布流页数

```http request
GET /home/get_page_amount
```

*没什么意义，实际上可以前端通过queryId < pageSize ? 1 : queryId / pageSize 计算获得，只有当pageSize日后不是常量时有用*

参数：

| 参数名      | 说明        |
|----------|-----------|
| query_id | 整数类型的检索ID |

***

# 用户登录

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
