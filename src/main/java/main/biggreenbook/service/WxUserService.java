package main.biggreenbook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.pojo.Follow;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.RedisHelper;
import main.biggreenbook.utils.StaticMappingHelper;
import main.biggreenbook.utils.UUIDGenerator;
import main.biggreenbook.utils.WxInfoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class WxUserService {

    public List<UserCard> getUserCards(int queryId, Map<String, Object> map) {
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if ((int) map.get("pageNum") == 0 && USER_RESULT_PAGE_SIZE < queryId) {
            result.addAll(userMapper.getUserCardBySearch(map));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < USER_RESULT_PAGE_SIZE ? 1 : queryId / USER_RESULT_PAGE_SIZE;
        int actualPage = pageAmount - 1 - (int) map.get("pageNum");

        map.replace("amount", USER_RESULT_PAGE_SIZE);
        map.replace("pageNum", actualPage);

        result.addAll(userMapper.getUserCardBySearch(map));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
        });
        return result;
    }

    public int getQueryId(String search) {
        return userMapper.getQueryId(search);
    }

    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < USER_RESULT_PAGE_SIZE ? 1 : queryId / USER_RESULT_PAGE_SIZE;
    }


    // 微信小程序登录 //
    // 微信小程序登录 //
    // 微信小程序登录 //

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
        //生成
        String customCode = UUIDGenerator.generate();
        //填充
        redisHelper.setCustomCode(customCode, resultOpenId, resultSessionKey, Duration.ofDays(1));
        //
        return customCode;
    }


    /**
     * 判断自定义登录记录可用性（是否过期
     *
     * @param customCode 自定义登录记录
     * @return 是否存在该记录
     */
    public boolean checkCustomCodeState(String customCode) {
        //查找是否存在
        return redisHelper.hasKey(customCode);
    }


    // 个人信息相关 //
    // 个人信息相关 //
    // 个人信息相关 //

    /**
     * 获取登录用户自己的个人信息
     *
     * @param customCode 自定义登录记录
     * @return 用户信息POJO类
     */
    public User getMyInfo(String customCode) {
        //从redis中获取uid
        String uid = redisHelper.getUidFromCustomCode(customCode);
        //通过uid获取用户
        return userMapper.getUserByUid(uid);
    }

    /**
     * 获取指定用户的信息
     *
     * @param uid 要获取的用户的uid
     * @return 用户信息pojo类
     */
    public User getInfo(String uid) {
        return userMapper.getUserByUid(uid);
    }


    // 用户收藏夹相关 //
    // 用户收藏夹相关 //
    // 用户收藏夹相关 //

    /**
     * 获取用户的收藏夹
     *
     * @param uid  用户的uid
     * @param page 页
     * @return 预览卡片
     */
    public List<PreviewCard> getCollections(String uid, int page) {
        return contentMapper.getUserCollections(uid, page, COLLECTION_PAGE_SIZE);
    }


    /**
     * 获取用户的收藏夹的页数
     *
     * @param uid 用户的uid
     * @return 页数
     */
    public int getCollectionPageAmount(String uid) {
        int amount = contentMapper.getUserCollectionAmount(uid);
        if (amount == 0) {
            return 0;
        } else if (amount < COLLECTION_PAGE_SIZE) {
            return 1;
        } else {
            return (amount / COLLECTION_PAGE_SIZE) + (amount % COLLECTION_PAGE_SIZE == 0 ? 1 : 0);
        }
    }

    
    // 个人信息页关注互动 //
    // 个人信息页关注互动 //
    // 个人信息页关注互动 //

    /**
     * 用户关注操作
     *
     * @param customCode 用户的登录CustomCode
     * @param goal_uid   用户要关注的目标用户UID
     * @return 如果本身就关注了，那么返回false（在前端的控制下这不应该发生
     */
    public boolean doFollow(String customCode, String goal_uid) {
<<<<<<< HEAD

=======
        if (!redisHelper.hasKey(customCode)) return false;
>>>>>>> 91d4fc78022e824967a2a37ac454e119208347ac
        if (getFollowState(customCode, goal_uid)) return false;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        userMapper.addFollow(uid, goal_uid, new Timestamp(Calendar.getInstance().getTimeInMillis()));
        return true;
    }

    /**
     * 用户取消关注操作
     *
     * @param customCode 用户的登录CustomCode
     * @param goal_uid   用户要取关的目标用户UID
     * @return 如果本身就没关注，那么返回false（在前端的控制下这不应该发生
     */
    public boolean doUnFollow(String customCode, String goal_uid) {
        if (!redisHelper.hasKey(customCode)) return false;
        if (!getFollowState(customCode, goal_uid)) return false;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        userMapper.deleteFollow(uid, goal_uid);
        return true;
    }

    /**
     * 返回是否已经关注
     *
     * @param customCode 用户的登录CustomCode
     * @param goal_uid   目标用户UID
     * @return 是否已经关注
     */
    public boolean getFollowState(String customCode, String goal_uid) {
        if (!redisHelper.hasKey(customCode)) return false;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        List<Follow> followStateBetween = userMapper.getFollowStateBetween(uid, goal_uid);
        return followStateBetween.size() != 0;
<<<<<<< HEAD

=======
>>>>>>> 91d4fc78022e824967a2a37ac454e119208347ac
    }

    //用户搜索结果每页展示的数量？//todo:
    private static final int USER_RESULT_PAGE_SIZE = 8;

    //用户收藏夹每页的卡片数量
    private static final int COLLECTION_PAGE_SIZE = 16;

    @Autowired
    private WxInfoContainer wxInfoContainer;

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private StaticMappingHelper staticMappingHelper;
}
