# 数据库数据结构说明

***

## 用户内容收藏表 collection

| column            | description |
|-------------------|-------------|
| uid, varchar(256) | 用户微信OpenID  |
| cid, varchar(256) | 收藏的内容ID     |
| date, datetime    | 收藏的时间       |

## 内容信息表 content

| column                    | description               |
|---------------------------|---------------------------|
| cid, varchar(256)         | 内容的ID                     |
| title, varchar(256)       | 内容的标题                     |
| main_text, mediumtext     | 内容的正文                     |
| date, datetime            | 内容的发布时间                   |
| type, varchar(32)         | 内容的类型，可为"picture"或"video" |
| like_amount, int unsigned | 内容的点赞数量                   |
| uid, varchar(256)         | 发布者                       |
| sid, varchar(256)         | 该内容使用的资源的ID               |

## 用户关注关系表 follow

| column                 | description |
|------------------------|-------------|
| uid, varchar(256)      | 被关注者ID      |
| follower, varchar(256) | 进行关注的人的ID   |
| date, datetime         | 关注时间        |

## 回复信息表 reply

| column                    | description                        |
|---------------------------|------------------------------------|
| rid, varchar(256)         | 回复的ID                              |
| type, varchar(32)         | 回复的类型，可为对内容回复("top")或楼中楼回复("inner) |
| content, text             | 回复的正文内容                            |
| goal, varchar(256)        | 回复的目标，目标可以是content的cid，也可以是回复的rid  |
| like_amount, int unsigned | 回复的点赞数量                            |
| date, datetime            | 回复的时间                              |
| uid, varchar(256)         | 回复的用户                              |

## 举报表 report

| column             | description    |
|--------------------|----------------|
| uid, varchar(256)  | 举报发起者ID        |
| cid, varchar(256)  | 被举报的内容         |
| date, datetime     | 举报发起的时间        |
| solved, tinyint(1) | 举报是(1)否(0)被处理了 |
| reason, text       | 举报的原因          |

## 资源表 resource

| column            | description                                                     |
|-------------------|-----------------------------------------------------------------|
| sid, varchar(256) | 资源的ID                                                           |
| type, varchar(32) | 资源的类型，可为"picture"或"video"                                       |
| paths, json       | 资源的路径Json数组，如```["img/lmlh.jpg", "img/SN_Soobrazitelny.jpg"]``` |

## 用户信息表 user

| column                    | description    |
|---------------------------|----------------|
| uid, varchar(256)         | 用户的微信OpenID    |
| nickname, varchar(256)    | 用户的昵称          |
| description, text         | 用户的自我描述        |
| gender, tinyint(1)        | 用户的性别，0女，1男    |
| birthday, date            | 用户的生日          |
| state, tinyint(1)         | 用户的状态，0正常，1被封禁 |
| avatar_path, varchar(256) | 用户的头像的路径       |

## 点赞记录表 likes

| column             | description                  |
|--------------------|------------------------------|
| goal, varchar(256) | 点赞的目标，可以是内容cid或评论rid         |
| type, varchar(32)  | 点赞的目标类型，可以是"content"或"reply" |
| uid, varchar(256)  | 点赞的人的uid                     |

## 站内消息表

