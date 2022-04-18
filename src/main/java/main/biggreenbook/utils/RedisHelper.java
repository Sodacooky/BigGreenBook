package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisHelper {

    public String getUidFromCustomCode(String customCode) {
        String value = (String) redisTemplate.opsForValue().get("customCode_" + customCode);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[0];
    }

    public String getSessionKeyFromCustomCode(String customCode) {
        String value = (String) redisTemplate.opsForValue().get("customCode_" + customCode);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[1];
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * @param customCode key
     * @param uid        part 1
     * @param sessionKey part 2
     * @param duration   expiration
     * @return 如果已经存在返回false
     */
    public boolean setCustomCode(String customCode, String uid, String sessionKey, Duration duration) {
        if (redisTemplate.hasKey("customCode_" + customCode)) return false;
        else {
            redisTemplate.opsForValue().set("customCode_" + customCode, uid + "_" + sessionKey, duration);
            return true;
        }
    }


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
}
