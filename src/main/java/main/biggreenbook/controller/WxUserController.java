package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        System.out.println(code);
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

    // 用户收藏夹相关 //
    // 用户收藏夹相关 //
    // 用户收藏夹相关 //

    /**
     * 获取用户的收藏夹内容
     * 用手收藏夹的内容更新不会很频繁，故不去排除重复
     *
     * @param uid  谁的收藏夹，用户id
     * @param page 页
     * @return 当前页的收藏内容预览卡片
     */
    @GetMapping("/get_collections")
    public List<PreviewCard> getCollections(@RequestParam("uid") String uid, @RequestParam("page") int page) {
        int collectionPageAmount = wxUserService.getCollectionPageAmount(uid);
        if (page >= collectionPageAmount) page = collectionPageAmount - 1;
        if (page < 0) page = 0;
        return wxUserService.getCollections(uid, page);
    }

    /**
     * 获取用户收藏夹的页数
     *
     * @param uid 谁的收藏夹，用户id
     * @return 页数，显然，当没有收藏内容时为0
     */
    @GetMapping("/get_collections_page_amount")
    public int getCollectionPageAmount(@RequestParam("uid") String uid) {
        return wxUserService.getCollectionPageAmount(uid);
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


    @GetMapping("/updateUser")
    public int updateUser(User user,Date date){
        //对前端的date参数转换成sql.date
        user.setBirthday(new java.sql.Date(date.getTime()));
        return   wxUserService.updateUser(user);
    }

    @Autowired
    private WxUserService wxUserService;
}
