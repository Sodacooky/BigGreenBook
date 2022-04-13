# Redis缓存数据结构

- - -

## 用户SessionKey缓存

Key:

sessionKey_{openId}

如：sessionKey_abc123

Value:

{SessionKey}

## 用户自定义登录记录字符串

Key:

customCode_{StateString}

如：customCode_abc114514

Value:

{OpenId}
