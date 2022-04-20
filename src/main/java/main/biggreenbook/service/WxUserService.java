package main.biggreenbook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.pojo.Follow;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.pojo.UserPrivacy;
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

@Service
public class WxUserService {

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
        //判断是否是初次登录（注册为新用户）
        if (userMapper.getUserByUid(resultOpenId) == null) {
            userMapper.addUser(new User(
                    resultOpenId,
                    "新用户" + resultOpenId,
                    "暂无简介",
                    1,
                    null,
                    0,
                    "avatar/default.jpg"));
        }
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
        User user = userMapper.getUserByUid(uid);
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

    //获取用户的关注者列表
    public List<UserCard> getFollowers(String customCode, String uid, int page) {
        //
        String me_uid = redisHelper.getUidFromCustomCode(customCode);
        //page check
        int followersAmount = userMapper.getUserFollowersAmount(me_uid);
        int followersPageAmount = followersAmount < USER_CARD_PAGE_SIZE ? 1 : (followersAmount / USER_CARD_PAGE_SIZE) + 1;
        if (page >= followersPageAmount) return new ArrayList<>();
        //
        List<UserCard> result = new ArrayList<>();
        //获取当前页关注者uid
        List<String> followersUid = userMapper.getFollowersUid(uid, page, USER_CARD_PAGE_SIZE);
        //注入信息
        followersUid.forEach(follower -> {
            User theFollower = userMapper.getUserByUid(follower);
            int theFollowerContentAmount = contentMapper.getUserContentAmount(follower);
            int theFollowerFansAmount = userMapper.getUserFollowersAmount(follower);
            int theFollowerStatus = userMapper.getFollowStateBetween(me_uid, follower).size();
            UserCard followerCard =
                    new UserCard(theFollower.getUid(),
                            theFollower.getNickname(),
                            theFollower.getAvatarPath(),
                            theFollowerContentAmount,
                            theFollowerFansAmount,
                            theFollowerStatus);
            result.add(followerCard);
        });
        //
        return result;
    }

    //获取用户的正在关注列表
    public List<UserCard> getFollowings(String customCode, String uid, int page) {
        //
        String me_uid = redisHelper.getUidFromCustomCode(customCode);
        //page check
        int followersAmount = userMapper.getUserFollowingsAmount(me_uid);
        int followersPageAmount = followersAmount < USER_CARD_PAGE_SIZE ? 1 : (followersAmount / USER_CARD_PAGE_SIZE) + 1;
        if (page >= followersPageAmount) return new ArrayList<>();
        //
        List<UserCard> result = new ArrayList<>();
        //获取当前页正在关注的uid
        List<String> followingsUid = userMapper.getFollowingsUid(me_uid, page, USER_CARD_PAGE_SIZE);
        //注入信息
        followingsUid.forEach(following -> {
            User theFollower = userMapper.getUserByUid(following);
            int theFollowerContentAmount = contentMapper.getUserContentAmount(following);
            int theFollowerFansAmount = userMapper.getUserFollowersAmount(following);
            int theFollowerStatus = userMapper.getFollowStateBetween(me_uid, following).size();
            UserCard followerCard =
                    new UserCard(theFollower.getUid(),
                            theFollower.getNickname(),
                            theFollower.getAvatarPath(),
                            theFollowerContentAmount,
                            theFollowerFansAmount,
                            theFollowerStatus);
            result.add(followerCard);
        });
        //
        return result;
    }

    // 用户收藏夹相关 //
    // 用户收藏夹相关 //
    // 用户收藏夹相关 //

    /**
     * 获取其他用户的收藏夹，会进行权限判定
     *
     * @param uid  用户的uid
     * @param page 页
     * @return 预览卡片数组，如果没有权限会返回空
     */
    public List<PreviewCard> getCollections(String uid, int page) {
        //check privacy
        UserPrivacy userPrivacy = userMapper.getUserPrivacy(uid);
        if (userPrivacy.getPublicCollection() == 0) {
            return new ArrayList<>();
        }
        //check page
        int collectionPageAmount = getCollectionPageAmount(uid);
        if (page >= collectionPageAmount) return new ArrayList<>();
        //do get
        return contentMapper.getUserCollections(uid, page, COLLECTION_PAGE_SIZE);
    }


    /**
     * 获取用户自己的收藏夹，没有权限判定
     *
     * @param customCode 用户的登录记录
     * @param page       页
     * @return 预览卡片数组，到达最后一页之后会返回空
     */
    public List<PreviewCard> getMyCollections(String customCode, int page) {
        //get uid
        String uid = redisHelper.getUidFromCustomCode(customCode);
        //check page
        int collectionPageAmount = getCollectionPageAmount(uid);
        if (page >= collectionPageAmount) return new ArrayList<>();
        //do get
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
        if (!redisHelper.hasKey(customCode)) return false;
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
    }

    // 更新用户信息 //
    // 更新用户信息 //
    // 更新用户信息 //

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 更新用户的隐私设定
     *
     * @param customCode  自定义登录状态记录字符串
     * @param userPrivacy 用户新的用户隐私设定
     * @return 是否成功
     */
    public boolean updateUserPrivacy(String customCode, UserPrivacy userPrivacy) {
        //check exist
        String uid = redisHelper.getUidFromCustomCode(customCode);
        UserPrivacy oldPrivacy = userMapper.getUserPrivacy(uid);
        //not exist create it
        if (oldPrivacy == null) userMapper.insertDefaultUserPrivacy(uid);
        //do update
        userMapper.updateUserPrivacy(userPrivacy);
        //just true
        return true;
    }

    //用户搜索结果每页展示的数量？//todo:
    private static final int USER_RESULT_PAGE_SIZE = 8;

    //用户收藏夹每页的卡片数量
    private static final int COLLECTION_PAGE_SIZE = 16;

    //用户关注、粉丝列表每页卡片数量
    private static final int USER_CARD_PAGE_SIZE = 16;

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
