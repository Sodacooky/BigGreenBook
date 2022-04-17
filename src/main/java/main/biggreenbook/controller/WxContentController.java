package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//微信小程序内容相关控制器
@RestController
@CrossOrigin
@RequestMapping("/home")
public class WxContentController {

    @Autowired
    WxContentService wxContentService;

    /***
     * 获取首页瀑布流卡片
     * @param page 当前浏览页检索ID，必须带有query_id否则这个页是没有意义的
     * @param query_id 记住当前分页状态，避免重复
     * @return 预览页卡片们
     */
    @GetMapping("/get")
    public List<PreviewCard> getPreviewCards(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;
        //to service
        return wxContentService.getPreviewCards(page,query_id);
    }

    @GetMapping("/get_contentInfo")
    public ContentInfo getContentInfo(String cid,String uid){
        return wxContentService.getContentInfo(cid,uid);
    }

    /**
     *
        用户进入内容详情页时，点赞与否只有两个值 0/1;
        用户进行 点赞/取消 时，提交与之原isLike相反的值
     * @param isLike    点赞与否：  1表示已点赞，0表示未点赞;
     * @param likeType  点赞类型    (取消点赞时，不需要此参数)
     * @param goal      点赞目标
     * @param uid       用户uid
     * @date 2022/4/16 10:42
     * @return int    返回点赞数
     */
    @GetMapping("/giveLike")
    public int giveLike(int isLike,String likeType,String goal,String uid){
        return wxContentService.giveLike(isLike,likeType,goal,uid);
    }

    /**
     *

     * @param isCollection    收藏与否：  1表示已收藏，0表示未收藏（同点赞）
     * @param cid               收藏内容
     * @param uid               收藏者
     * @date 2022/4/16 19:17
     * @return int
     */
    @GetMapping("/collection")
    public int collectionContent(int isCollection,String cid,String uid){
        return wxContentService.collectionContent(isCollection,cid,uid);
    }

    /**
     *

     * @param uid       举报发起者
     * @param cid       举报内容
     * @param reason    举报原因
     * @date 2022/4/16 17:53
     * @return int
     */
    @GetMapping("/report")
    public int reportContent(String uid, String cid,String reason){
        Timestamp date= new Timestamp(System.currentTimeMillis());
        return wxContentService.reportContent(uid,cid,reason,date);
    }


    /**
     *  获取检索ID
     */
    @GetMapping("/get_query_id")
    public int getHomeQueryId() {
        return wxContentService.getQueryId(null);
    }


    /***
     * 获取一共有多少页
     * @param query_id 检索ID
     * @return
     */
    @GetMapping("/get_page_amount")
    public int getHomePageAmount(@RequestParam(required = true) int query_id) {
        return wxContentService.getPageAmount(query_id);
    }
}