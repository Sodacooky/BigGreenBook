package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//微信小程序内容相关控制器
@RestController
@CrossOrigin
@RequestMapping("/ctn")
public class WxContentController {

    // 首页瀑布流 //
    // 首页瀑布流 //
    // 首页瀑布流 //


    /**
     * 获取首页瀑布流检索ID
     */
    @GetMapping("/get_home_query_id")
    public int getHomeQueryId() {
        return wxContentService.getHomePageQueryId();
    }

    /**
     * 获取首页瀑布流卡片
     *
     * @param page     当前浏览页检索ID，必须带有query_id否则这个页是没有意义的
     * @param query_id 记住当前分页状态，避免重复
     * @return 预览页卡片们
     */
    @GetMapping("/get_home_page")
    public List<PreviewCard> getPreviewCards(@RequestParam int page, @RequestParam int query_id) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) return new ArrayList<>();
        //to service
        return wxContentService.getHomePageContent(page, query_id);
    }

    /***
     * 获取一共有多少页
     * @param query_id 检索ID
     * @return 页数
     */
    @GetMapping("/get_home_page_amount")
    public int getHomePageAmount(@RequestParam(required = true) int query_id) {
        return wxContentService.getPageAmount(query_id);
    }

    // 内容详情 //
    // 内容详情 //
    // 内容详情 //

    /**
     * 获取内容详情
     *
     * @param cid
     * @param customCode
     * @return
     */
    @GetMapping("/get_contentInfo")
    public ContentInfo getContentInfo(String cid, String customCode) {
        return wxContentService.getContentInfo(cid, customCode);
    }

    // 内容互动 //
    // 内容互动 //
    // 内容互动 //

    /**
     * 点赞
     *
     * @param goal_id    点赞目标id （cid）
     * @param customCode 用户自己的自定义登录记录字符串
     * @param likeType   点赞类型；content 对内容点赞/ reply 对评论点赞
     * @return int    返回点赞数
     * @date 2022/4/16 10:42
     */
    @GetMapping("/giveLike")
    public int giveLike(String goal_id, String customCode, String likeType) {
        return wxContentService.giveLike(goal_id, customCode, likeType);
    }

    /**
     * 取消点赞
     *
     * @param goal_id    点赞目标id （cid）
     * @param customCode 用户自己的自定义登录记录字符串
     * @return int    返回点赞数
     * @date 2022/4/16 10:42
     */
    @GetMapping("/ungiveLike")
    public int ungiveLike(String goal_id, String customCode) {
        return wxContentService.ungiveLike(goal_id, customCode);
    }

    /**
     * 收藏内容
     *
     * @param cid        收藏内容id
     * @param customCode 用户自己的自定义登录记录字符串
     * @return boolean    ture / fasle 成功/失败
     * @date 2022/4/16 19:17
     */
    @GetMapping("/collection")
    public boolean collectionContent(String cid, String customCode) {
        return wxContentService.addCollectionContent(cid, customCode);
    }

    /**
     * 取消收藏内容
     *
     * @param cid        收藏内容id
     * @param customCode 用户自己的自定义登录记录字符串
     * @return boolean    ture / fasle 成功/失败
     * @date 2022/4/16 19:17
     */
    @GetMapping("/uncollection")
    public boolean uncollectionContent(String cid, String customCode) {
        return wxContentService.deleteCollectionContent(cid, customCode);
    }

    /**
     * 举报内容
     *
     * @param customCode 举报发起者,用户自己的自定义登录记录字符串
     * @param cid        举报内容
     * @param reason     举报原因
     * @return boolean
     * @date 2022/4/16 17:53
     */
    @GetMapping("/report")
    public boolean reportContent(String customCode, String cid, String reason) {
        return wxContentService.reportContent(customCode, cid, reason);
    }

    /**
     * 发布内容
     *
     * @param content title 内容标题
     *                mainText 正文
     *                type  资源类型
     *                uid   发布者customCode
     *                sid   资源id
     * @return boolean 发布成功与否
     * @date 2022/4/20 16:46
     */
    @PostMapping("/publish_content")
    public boolean publishContent(@RequestParam("customCode") String customCode, @RequestBody Content content) {
        return wxContentService.publishContent(customCode, content);
    }

    /**
     * 修改发布的内容
     *
     * @param cid
     * @param title
     * @param mainText
     * @param sid
     * @return boolean
     * @date 2022/4/20 17:56
     */
    @PostMapping("/update_content")
    public boolean updateContent(Content content) {
        return wxContentService.updateContent(content);
    }


    // 内容评论 //
    // 内容评论 //
    // 内容评论 //


    @Autowired
    WxContentService wxContentService;

}
