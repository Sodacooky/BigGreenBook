package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.ReplyVO;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public boolean collectionContent(@RequestParam("customCode") String customCode, @RequestParam("cid") String cid) {
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
    public boolean uncollectionContent(@RequestParam("customCode") String customCode, @RequestParam("cid") String cid) {
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

    // 内容发布 //
    // 内容发布 //
    // 内容发布 //

    /**
     * 发布内容
     *
     * @param content title 内容标题
     *                mainText 正文
     *                type  资源类型
     *                uid   发布者customCode
     *                sid   资源id
     *                tags  标签
     * @return boolean 发布成功与否
     * @date 2022/4/20 16:46
     */
    @PostMapping("/publish_content")
    public boolean publishContent(@RequestParam("customCode") String customCode,
                                  @RequestParam("title") String title,
                                  @RequestParam("mainText") String mainText,
                                  @RequestParam("tags") String tagsJson,
                                  @RequestParam("sid") String sid,
                                  @RequestParam("type") String type) {

        Content content = new Content();
        content.setTitle(title);
        content.setMainText(mainText);
        content.setTags(tagsJson);
        content.setSid(sid);
        content.setType(type);
        return wxContentService.publishContent(customCode, content);
    }

    /**
     * 修改发布的内容
     *
     * @param cid      内容id
     * @param title    内容标题
     * @param mainText 内容正文
     * @param sid      资源id
     * @param tags     标签
     * @return boolean
     * @date 2022/4/20 17:56
     */
    @PostMapping("/update_content")
    public boolean updateContent(Content content) {
        return wxContentService.updateContent(content);
    }


    @GetMapping("/remove_content")
    public boolean removeContent(@RequestParam("customCode") String customCode, @RequestParam("cid") String cid) {
        return wxContentService.removeContent(customCode, cid);
    }

    /**
     * 指示开始上传文件，如果调用该方法后半小时仍然没有指示结束上传，那么资源将作废
     *
     * @param customCode 用户自定义登录记录
     * @param type       上传的资源类型，对应正在上传的内容的类型，可为"picture"/"video"
     * @return 当前上传文件操作的ID，在后续上传操作中需要用到，失败返回空字符串
     */
    @GetMapping("/start_upload")
    public String startUploadFile(@RequestParam("customCode") String customCode, @RequestParam("type") String type) {
        return wxContentService.startUploadFile(customCode, type);
    }


    /**
     * 上传文件
     *
     * @param uploadId 在指示开启上传文件方法中获得的上传ID
     * @param file     要上传的文件
     * @return 成功返回true，失败（文件类型非法）时返回false
     */
    @PostMapping("/do_upload")
    public boolean uploadFile(@RequestParam("uploadId") String uploadId, @RequestParam("file") MultipartFile file) {
        return wxContentService.uploadFile(uploadId, file);
    }

    /**
     * 指示结束上传文件，将之前上传的文件合并为一个资源并获得其资源sid
     *
     * @param uploadId 上传ID
     * @return 资源ID，sid,需要填写到发布内容的sid属性内，失败返回空字符串
     */
    @GetMapping("/finish_upload")
    public String finishUploadFile(@RequestParam("uploadId") String uploadId) {
        return wxContentService.finishUploadFile(uploadId);
    }

    // 内容评论 //
    // 内容评论 //
    // 内容评论 //

    /**
     * 获取评论
     *
     * @param cid 内容的cid
     * @return 双层评论内容
     */
    @GetMapping("/get_reply")
    public List<ReplyVO> getReply(@RequestParam("cid") String cid) {
        return wxContentService.getReply(cid);
    }

    /**
     * 发表评论
     *
     * @param customCode 用户customCode
     * @param goal_id    目标id
     * @param goal_type  目标类型
     * @param content    发什么
     * @return 是否成功
     */
    @GetMapping("/add_reply")
    public boolean addReply(@RequestParam("customCode") String customCode,
                            @RequestParam("goal") String goal_id,
                            @RequestParam("type") String goal_type,
                            @RequestParam("content") String content) {
        goal_type = goal_type.toLowerCase();
        return wxContentService.addReply(customCode, goal_id, goal_type, content);
    }


    @Autowired
    WxContentService wxContentService;

}
