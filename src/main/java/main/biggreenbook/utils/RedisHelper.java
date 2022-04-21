package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisHelper {

    //用户登录状态相关//
    //用户登录状态相关//
    //用户登录状态相关//

    //通过customCode key找到提取对应的uid
    public String getUidFromCustomCode(String customCode) {
        String value = (String) redisTemplate.opsForValue().get("customCode_" + customCode);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[0];
    }

    //通过customCode key找到提取对应的sessionKey
    public String getSessionKeyFromCustomCode(String customCode) {
        String value = (String) redisTemplate.opsForValue().get("customCode_" + customCode);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[1];
    }


    /**
     * @param customCode key
     * @param uid        part 1
     * @param sessionKey part 2
     * @param duration   expiration
     * @return 如果已经存在返回false
     */
    public boolean setCustomCode(String customCode, String uid, String sessionKey, Duration duration) {
        if (hasKey("customCode_" + customCode)) return false;
        else {
            redisTemplate.opsForValue().set("customCode_" + customCode, uid + "_" + sessionKey, duration);
            return true;
        }
    }

    //判断customCode是否存在（是否过期
    public boolean hasCustomCode(String customCode) {
        return hasKey("customCode_" + customCode);
    }


    // 文件上传相关 //
    // 文件上传相关 //
    // 文件上传相关 //

    /**
     * 添加一条文件上传记录缓存
     *
     * @param uploadId key,
     * @param sid      part 1
     * @param type     part 2
     * @return 如果已经存在返回false
     */
    public boolean setUploadId(String uploadId, String sid, String type) {
        if (hasKey("uploading_" + uploadId)) return false;
        else {
            redisTemplate.opsForValue().set("uploading_" + uploadId, sid + "_" + type, Duration.ofMinutes(30));
            return true;
        }
    }

    /**
     * （结束上传后）移除一条文件上传记录
     *
     * @param uploadId key
     * @return 如果本身不存在，返回false
     */
    public boolean removeUploadId(String uploadId) {
        if (!hasKey("uploading_" + uploadId)) return false;
        else return Boolean.TRUE.equals(redisTemplate.delete(uploadId));
    }

    public boolean hasUploadId(String uploadId) {
        return hasKey("uploading_" + uploadId);
    }

    public String getUploadSid(String uploadId) {
        String value = (String) redisTemplate.opsForValue().get("uploading_" + uploadId);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[0];
    }

    public String getUploadType(String uploadId) {
        String value = (String) redisTemplate.opsForValue().get("uploading_" + uploadId);
        if (value == null) return "null";
        String[] values = value.split("_");
        return values[1];
    }

    // 特殊情况下才使用 //
    // 特殊情况下才使用 //
    // 特殊情况下才使用 //


    //redis hasKey的封装
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;
}
