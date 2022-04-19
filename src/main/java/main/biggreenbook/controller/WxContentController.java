package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    /***
     * 获取首页瀑布流卡片
     * @param page 当前浏览页检索ID，必须带有query_id否则这个页是没有意义的
     * @param query_id 记住当前分页状态，避免重复
     * @return 预览页卡片们
     */
    @GetMapping("/get_home_page")
    public List<PreviewCard> getPreviewCards(@RequestParam int page, @RequestParam int query_id) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;
        //to service
        return wxContentService.getHomePageContent(page, query_id);
    }

    // 搜索 //
    // 搜索 //
    // 搜索 //

    /**
     * 获取搜索的检索ID
     *
     * @param search 搜索的内容
     * @return 检索ID
     */
//    @GetMapping("/get_search_query_id")
//    public int getSearchQueryId(@RequestParam("search") String search) {
//        return wxContentService.getSearchQueryId(search);
//    }

    /**
     * 内容搜索
     *
     * @param page     页
     * @param query_id 检索iD
     * @param search   搜索内容
     * @param sort     搜索结果的排序方式，LATEST/HOT
     * @return 结果内容预览卡片
     */
//    @GetMapping("/get_search")
//    public List<PreviewCard> getPreviewCardsBySearch(@RequestParam int page,
//                                                     @RequestParam int query_id,
//                                                     @RequestParam String search,
//                                                     @RequestParam String sort) {
//        //page parameter check
//        if (page < 0) page = 0;
//        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;
//        sort = sort.toUpperCase();
//        if (!sort.equals("HOT") && !sort.equals("LATEST")) sort = "HOT";
//        //to service
//        return wxContentService.getSearchContent(page, query_id, search, sort);
//    }


    // 内容详情 //
    // 内容详情 //
    // 内容详情 //

    /**
     * @param cid
     * @param uid
     * @return
     */
    @GetMapping("/get_contentInfo")
    public ContentInfo getContentInfo(String cid, String uid) {
        return wxContentService.getContentInfo(cid, uid);
    }

    // 内容互动 //
    // 内容互动 //
    // 内容互动 //


    /**
     * 用户进入内容详情页时，点赞与否只有两个值 0/1;
     * 用户进行 点赞/取消 时，提交与之原isLike相反的值
     *
     * @param isLike   点赞与否：  1表示已点赞，0表示未点赞;
     * @param likeType 点赞类型    (取消点赞时，不需要此参数)
     * @param goal     点赞目标
     * @param uid      用户uid
     * @return int    返回点赞数
     * @date 2022/4/16 10:42
     */
    @GetMapping("/giveLike")
    public int giveLike(int isLike, String likeType, String goal, String uid) {
        return wxContentService.giveLike(isLike, likeType, goal, uid);
    }
    

    /**
     * @param isCollection 收藏与否：  1表示已收藏，0表示未收藏（同点赞）
     * @param cid          收藏内容
     * @param uid          收藏者
     * @return int
     * @date 2022/4/16 19:17
     */
    @GetMapping("/collection")
    public int collectionContent(int isCollection, String cid, String uid) {
        return wxContentService.collectionContent(isCollection, cid, uid);
    }

    /**
     * @param uid    举报发起者
     * @param cid    举报内容
     * @param reason 举报原因
     * @return int
     * @date 2022/4/16 17:53
     */
    @GetMapping("/report")
    public int reportContent(String uid, String cid, String reason) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        return wxContentService.reportContent(uid, cid, reason, date);
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


    @Autowired
    WxContentService wxContentService;

}
