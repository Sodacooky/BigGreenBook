package main.biggreenbook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.StaticMappingHelper;
import main.biggreenbook.utils.UUIDGenerator;
import main.biggreenbook.utils.WxInfoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WxUserService {

    /**
     * 处理登录
     *
     * @param jsCode 前端登录的Code
     * @return 自定义状态码（用于储存在微信小程序用户手机上以实现近期自动登录）
     */
    public String login(String jsCode) {
        //fill value
        String appId = wxInfoContainer.getAppId();
        String secret = wxInfoContainer.getSecret();
        String wxLoginApiURL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&";
        String withParams = String.format("appid=%s&secret=%s&js_code=%s", appId, secret, jsCode);
        //do get
        String resultOpenId, resultSessionKey;
        try {
            JsonNode jsonNode = new ObjectMapper().readValue(new URL(wxLoginApiURL + withParams), JsonNode.class);
            resultOpenId = jsonNode.get("openid").asText();
            resultSessionKey = jsonNode.get("session_key").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //将sessionKey储存到redis中
        redisTemplate.opsForValue().set("sessionKey_" + resultOpenId, resultSessionKey, Duration.ofDays(1));
        //生成登录自定义状态码
        String customCode = UUIDGenerator.generate();
        //储存到redis中
        redisTemplate.opsForValue().set("customCode_" + customCode, resultOpenId, Duration.ofDays(1));
        //
        return customCode;
    }

    /**
     * 使用登录记录进行自动登录
     *
     * @param customCode 自定义状态码
     * @return 是否存在该记录，是否登录成功
     */
    public boolean tryLoginWithCustomCode(String customCode) {
        //查找是否存在
        return Boolean.TRUE.equals(redisTemplate.hasKey("customCode_" + customCode));
    }

    //默认获取8个卡片
    private static final int PAGESIZE = 8;

    @Autowired
    private WxInfoContainer wxInfoContainer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
}
