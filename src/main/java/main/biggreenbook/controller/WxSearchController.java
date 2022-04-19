package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.service.WxSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信搜索相关控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/search")
public class WxSearchController {

    // 内容搜索 //
    // 内容搜索 //
    // 内容搜索 //

    /**
     * 搜索内容
     *
     * @param page     页
     * @param query_id 检索ID
     * @param search   搜索内容
     * @param sort     排序方式，LATEST/HOT，若不为这二者则默认为HOT
     * @return 搜索结果内容的预览卡片数组
     */
    @GetMapping("/do_content_search")
    public List<PreviewCard> doContentSearch(@RequestParam int page, @RequestParam int query_id, @RequestParam String search, String sort) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getContentSearchPageAmount(query_id)) page = getContentSearchPageAmount(query_id);
        //sort check
        sort = sort.toUpperCase();
        if (!sort.equals("HOT") && !sort.equals("LATEST")) sort = "HOT";
        //to service
        return wxSearchService.doSearchContent(query_id, search, page, sort);
    }

    /**
     * 获取搜索内容检索ID
     *
     * @param search 搜索内容
     * @return 检索ID
     */
    @GetMapping("/get_content_search_query_id")
    public int getContentSearchQueryId(@RequestParam String search) {
        return wxSearchService.getPreviewCardQueryId(search);
    }

    /**
     * 获取搜索内容页数量
     *
     * @param query_id 检索ID
     * @return 搜索内容页数
     */
    @GetMapping("/get_content_search_page_amount")
    public int getContentSearchPageAmount(@RequestParam int query_id) {
        return wxSearchService.getPreviewCardPageAmount(query_id);
    }

    // 用户搜索 //
    // 用户搜索 //
    // 用户搜索 //

    /**
     * @param page     页
     * @param query_id 检索ID
     * @param sort     排序方式，可以为null，默认为null时，按昵称排序；    "FANS"按粉丝数量排序
     * @param search   搜索内容，不可为null;
     * @param follower 关注者uid， 即当前用户uid
     * @return 用户信息预览卡片数组
     */
    @GetMapping("/do_user_search")
    public List<UserCard> doUserSearch(@RequestParam int page,
                                       @RequestParam int query_id,
                                       @RequestParam String sort,
                                       @RequestParam String search,
                                       @RequestParam String follower) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getUserCardsPageAmount(query_id)) page = getUserCardsPageAmount(query_id) - 1;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", PAGESIZE);
        map.put("amount", query_id % PAGESIZE);
        map.put("sort", sort);
        map.put("search", search);
        map.put("follower", follower);

        //to service
        return wxSearchService.getUserCardsBySearch(query_id, map);
    }

    @GetMapping("/get_userCard_query_id")
    public int getSearchQueryId(String search) {
        return wxSearchService.getUserCardQueryId(search);
    }

    /**
     * 获取一共有多少页
     *
     * @param query_id 检索ID
     * @return
     */
    @GetMapping("/get_userCard_page_amount")
    public int getUserCardsPageAmount(@RequestParam(required = true) int query_id) {
        return wxSearchService.getUserCardPageAmount(query_id);
    }


    private static final int PAGESIZE = 8;


    @Autowired
    WxSearchService wxSearchService;
}
