package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.pojo.UserPrivacy;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 微信用户相关控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/usr")
public class WxUserController {

    // 微信小程序登录 //
    // 微信小程序登录 //
    // 微信小程序登录 //

    /**
     * 登录
     *
     * @param code 来自小程序wx.login的jscode
     * @return 自定义登录记录，用于近期自动登录
     */
    @GetMapping("/login")
    public String login(@RequestParam("code") String code) {
        return wxUserService.login(code);
    }

    /**
     * 测试自动登录是否过期
     *
     * @param customCode 自定义登录记录
     * @return 是否成功
     */
    @GetMapping("/checkCustomCodeState")
    public boolean checkCustomCodeState(@RequestParam("customCode") String customCode) {
        return wxUserService.checkCustomCodeState(customCode);
    }

    // 个人信息相关 //
    // 个人信息相关 //
    // 个人信息相关 //

    /**
     * 通过登录时获取的自定义登录记录，获得自己的用户信息
     *
     * @param customCode 自定义登录记录
     * @return 用户信息POJO类
     */
    @GetMapping("/get_my_info")
    public User getMyInfo(@RequestParam("customCode") String customCode) {
        return wxUserService.getMyInfo(customCode);
    }

    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return 用户信息POJO类
     */
    @GetMapping("/get_info")
    public User getInfo(@RequestParam("uid") String uid) {
        return wxUserService.getInfo((uid));
    }

    /**
     * 获取某用户的关注者列表
     *
     * @param customCode 用户自定义登录记录字符串
     * @param uid        要查看的用户的id
     * @param page       页
     * @return 用户预览卡片数组，到底了返回空
     */
    @GetMapping("/get_followers")
    public List<UserCard> getFollowers(@RequestParam("customCode") String customCode,
                                       @RequestParam("uid") String uid,
                                       @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getFollowers(customCode, uid, page);
    }

    /**
     * 获取某用户的正在关注的人的列表
     *
     * @param customCode 用户自定义登录记录字符串
     * @param uid        要查看的用户的id
     * @param page       页
     * @return 用户预览卡片数组
     */
    @GetMapping("/get_followings")
    public List<UserCard> getFollowings(@RequestParam("customCode") String customCode,
                                        @RequestParam("uid") String uid,
                                        @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getFollowings(customCode, uid, page);
    }

    /**
     * 获取用户的粉丝数量，不需要权限判断
     *
     * @param uid 要查看的用户的id
     * @return 粉丝数量
     */
    @GetMapping("/get_follower_amount")
    public int getFollowerAmount(@RequestParam("uid") String uid) {
        return wxUserService.getFollowerAmount(uid);
    }

    /**
     * 获取用户的正在关注数量，不需要判断权限
     *
     * @param uid 要查看的用户id
     * @return 正在关注数量
     */
    @GetMapping("/get_following_amount")
    public int getFollowingAmount(@RequestParam("uid") String uid) {
        return wxUserService.getFollowingAmount(uid);
    }


    // 用户收藏夹，赞过的内容，自己发布的内容 //
    // 用户收藏夹，赞过的内容，自己发布的内容 //
    // 用户收藏夹，赞过的内容，自己发布的内容 //


    /**
     * 获取用户的收藏夹内容
     * 用手收藏夹的内容更新不会很频繁，故不去排除重复
     *
     * @param uid  谁的收藏夹，用户id
     * @param page 页
     * @return 当前页的收藏内容预览卡片（如果用户设置了隐藏，那么返回空数组
     */
    @GetMapping("/get_collections")
    public List<PreviewCard> getCollections(@RequestParam("uid") String uid, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getCollections(uid, page);
    }

    /**
     * 获取用户自己的收藏夹内容，不会判断权限
     *
     * @param customCode 用户自己的登录记录
     * @param page       页
     * @return 当前页的收藏内容预览卡片
     */
    @GetMapping("/get_my_collections")
    public List<PreviewCard> getMyCollections(@RequestAttribute("customCode") String customCode, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getMyCollections(customCode, page);
    }


    /**
     * 获取用户赞过的内容，会进行权限判断
     *
     * @param uid  要查看的用户uid
     * @param page 页
     * @return 赞过的内容的预览卡片数组
     */
    @GetMapping("/get_liked")
    public List<PreviewCard> getLiked(@RequestParam("uid") String uid, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getLiked(uid, page);
    }


    /**
     * 获取用户自己赞过的内容，不会进行权限判断
     *
     * @param customCode 用户的自定义登录记录
     * @param page       页
     * @return 赞过的内容的预览卡片数组
     */
    @GetMapping("/get_my_liked")
    public List<PreviewCard> getMyLiked(@RequestParam("customCode") String customCode, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getMyLiked(customCode, page);
    }


    /**
     * 获取用户发布的内容
     *
     * @param uid  要查看的用户uid
     * @param page 页
     * @return 内容的预览卡片数组
     */
    @GetMapping("/get_published")
    public List<PreviewCard> getPublished(@RequestParam("uid") String uid, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getPublished(uid, page);
    }


    /**
     * 获取用户自己发布的内容，只是个方便接口
     *
     * @param customCode 用户的自定义登录记录
     * @param page       页
     * @return 内容的预览卡片数组
     */
    @GetMapping("/get_my_published")
    public List<PreviewCard> getMyPublished(@RequestParam("customCode") String customCode, @RequestParam("page") int page) {
        if (page < 0) page = 0;
        return wxUserService.getMyPublished(customCode, page);
    }

    // 个人信息页关注互动 //
    // 个人信息页关注互动 //
    // 个人信息页关注互动 //

    /**
     * 进行用户关注
     *
     * @param customCode 发起人（用户自己）的自定义登录记录字符串
     * @param goal_uid   要关注的认的uid
     * @return 是否成功
     */
    @GetMapping("/follow")
    public boolean doFollow(@RequestParam("customCode") String customCode, @RequestParam("goal") String goal_uid) {
        return wxUserService.doFollow(customCode, goal_uid);
    }

    /**
     * 进行用户取消关注
     *
     * @param customCode 发起人（用户自己）的自定义登录记录字符串
     * @param goal_uid   要取消关注的认的uid
     * @return 是否成功
     */
    @GetMapping("/unfollow")
    public boolean doUnFollow(@RequestParam("customCode") String customCode, @RequestParam("goal") String goal_uid) {
        return wxUserService.doUnFollow(customCode, goal_uid);
    }

    /**
     * 获取用户关注状态
     *
     * @param customCode 用户自己的自定义登录记录字符串
     * @param goal_uid   目标用户的uid
     * @return 是否关注
     */
    @GetMapping("/get_follow_state")
    public boolean doIsFollowed(@RequestParam("customCode") String customCode, @RequestParam("goal") String goal_uid) {
        return wxUserService.getFollowState(customCode, goal_uid);
    }


    // 用户信息修改 //
    // 用户信息修改 //
    // 用户信息修改 //

    /**
     * 更新用户基本信息
     *
     * @param user 新的用户基本信息，部分属性为空
     * @return 返回是否修改成功
     */
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestParam("customCode") String customCode, @RequestBody User user) {
        return wxUserService.updateUser(customCode, user);
    }

    /**
     * 更新用户的隐私设定
     *
     * @param customCode       用户的自定义登录记录字符串
     * @param publicCollection 是否公开收藏
     * @param publicLiked      是否公开赞过
     * @return 是否成功
     */
    @PostMapping("/update_user_privacy")
    public boolean updateUserPrivacy(@RequestParam("customCode") String customCode,
                                     @RequestParam("publicCollection") boolean publicCollection,
                                     @RequestParam("publicLiked") boolean publicLiked) {
        UserPrivacy userPrivacy = new UserPrivacy(null, publicCollection ? 1 : 0, publicLiked ? 1 : 0);
        return wxUserService.updateUserPrivacy(customCode, userPrivacy);
    }


    /**
     * 用户更新头像
     *
     * @param customCode 用好谁创应酬登录记录字符串
     * @param file       要更换的新的头像文件
     * @return 是否成功
     */
    @PostMapping("/update_user_avatar")
    public boolean updateUserAvatar(@RequestParam("customCode") String customCode,
                                    @RequestParam("file") MultipartFile file) {
        return wxUserService.updateUserAvatar(customCode, file);
    }

    @Autowired
    private WxUserService wxUserService;
}
