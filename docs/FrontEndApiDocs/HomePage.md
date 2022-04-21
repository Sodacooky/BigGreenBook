# 首页

***

## 获取首页瀑布流卡片

```http request
GET /ctn/get_home_page
```

参数：

| 参数名      | 说明                                                              |
|----------|-----------------------------------------------------------------|
| page     | 获取的页数                                                           |
| query_id | 当前时刻(或这一次)进行获取检索使用的ID，用于保证返回的数据不出现，使用```/home/get_query_id```获取 |

返回：

PreviewCard数组，PreviewCard类型如下：

| 属性              | 类型    | 说明                                     |
| ----------------- | ------- | ---------------------------------------- |
| contentCid        | String  | 内容的ID                                 |
| resourceType      | String  | 资源的类型，为"picture"或"video"         |
| resourcePath      | String  | 用于卡片显示的资源URL，为图片或视频的URL |
| contentTitle      | String  | 内容的标题                               |
| userAvatarPath    | String  | 该内容作者的头像的图片的URL              |
| userNickname      | String  | 该内容作者的昵称                         |
| contentLikeAmount | Integer | 该内容的点赞数量                         |

***

## 获得首页瀑布流卡片获取检索ID

```http request
GET /ctn/get_home_query_id
```

返回：

整数类型的检索ID

***

## 获得首页瀑布流页数

```http request
GET /ctn/get_home_page_amount
```

*没什么意义，实际上可以前端通过queryId < pageSize ? 1 : queryId / pageSize 计算获得，只有当pageSize日后不是常量时有用*

参数：

| 参数名      | 说明        |
|----------|-----------|
| query_id | 整数类型的检索ID |





## 获取内容详情页

```java
GET /ctn/get_contentInfo
```

参数：

| 参数名     | 说明                                     |
| ---------- | ---------------------------------------- |
| cid        | 内容cid                                  |
| customCode | 发起人（用户自己）的自定义登录记录字符串 |

返回：

vo类ContentInfo属性如下：

| 属性           | 类型         | 说明                                     |
| -------------- | ------------ | ---------------------------------------- |
| cid            | String       | 内容的ID                                 |
| title          | String       | 内容标题                                 |
| text           | String       | 内容正文                                 |
| date           | String       | 内容发布时间                             |
| likeAmount     | String       | 该内容的点赞数                           |
| resourceType   | String       | 资源的类型，为"picture"或"video"         |
| sid            | String       | 资源id `无用`                            |
| path           | String       | 一连串资源URL，无意义。使用`paths`即可。 |
| paths          | List<String> | 用于卡片显示的资源URL，为图片或视频的URL |
| uid            | String       | 该内容作者的id                           |
| userNickname   | String       | 该内容作者的昵称                         |
| userAvatarPath | String       | 该内容作者的头像的图片的URL              |





## 内容点赞

```java
GET /ctn/giveLike
```

参数

| 参数名     | 说明                                             |
| ---------- | ------------------------------------------------ |
| goal_id    | 点赞目标id（内容id）                             |
| customCode | 发起人（用户自己）的自定义登录记录字符串         |
| likeType   | 点赞类型；`content`对内容点赞、`reply`对评论点赞 |

返回

该目标id的点赞数量  int



## 取消点赞

```java
GET /ctn/ungiveLike
```

参数

| 参数名     | 说明                                     |
| ---------- | ---------------------------------------- |
| goal_id    | 点赞目标id（内容id）                     |
| customCode | 发起人（用户自己）的自定义登录记录字符串 |

返回

该目标id的点赞数量  int



## 收藏内容

```java
GET /ctn/collection
```

参数

| 参数名     | 说明                                     |
| ---------- | ---------------------------------------- |
| goal       | 点赞目标id（内容id）                     |
| customCode | 发起人（用户自己）的自定义登录记录字符串 |

返回：

收藏成功与否   boolean



## 取消收藏

```java
GET /ctn/uncollection
```

参数

| 参数名     | 说明                                     |
| ---------- | ---------------------------------------- |
| goal       | 点赞目标id（内容id）                     |
| customCode | 发起人（用户自己）的自定义登录记录字符串 |

返回：

收藏成功与否   boolean



## 举报内容

```java
GET /ctn/report
```

参数

| 参数名     | 说明                                     |
| ---------- | ---------------------------------------- |
| customCode | 发起人（用户自己）的自定义登录记录字符串 |
| cid        | 举报内容id                               |
| reason     | 举报原因                                 |

返回：

举报成功与否   boolean



## 发布内容

```java
POST /ctn/publish_content
```

参数：

| 参数名   | 说明                                      |
| -------- | ----------------------------------------- |
| title    | 内容标题                                  |
| mainText | 内容正文                                  |
| type     | 资源类型  为"picture"或"video"            |
| uid      | 发布者uid，上传发布者的 customCode 即可。 |
| sid      | 资源id                                    |

返回：

发布成功与否 boolean



## 修改发布的内容

```java
POST /ctn/update_content
```

参数

| 参数名   | 说明     |
| -------- | -------- |
| cid      | 内容的id |
| title    | 内容标题 |
| mainText | 正文     |
| sid      | 资源id   |

返回：

修改成功与否 boolean
