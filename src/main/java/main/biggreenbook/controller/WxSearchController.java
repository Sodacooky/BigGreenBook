package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;

import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.service.WxSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class WxSearchController {

    @Autowired
    WxSearchService wxSearchService;

    /**
     *

     * @param page
     * @param query_id
     * @param search       搜索内容
     * @param sort         排序方式，可以为null，默认为null时，按时间排序；"HOT" 按点赞数量排序
     * @date 2022/4/17 10:20
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    @GetMapping("/get_previewCards")
    public List<PreviewCard> getPreviewCardsBySearch(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id, String search, String sort){
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getPreviewCardPageAmount(query_id)) page = getPreviewCardPageAmount(query_id) - 1;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",PAGESIZE);
        map.put("amount",query_id % PAGESIZE);
        map.put("search",search);
        map.put("sort",sort);

        //to service
        return wxSearchService.getPreviewCardsBySearch(query_id,map);
    }

    /**
     *  获取检索ID
     *  @Prama search   搜索内容
     */
    @GetMapping("/get_previewCard_query_id")
    public int getPreviewCardQueryId(String search) {
        return wxSearchService.getPreviewCardQueryId(search);
    }

    /**
     * 获取一共有多少页
     * @param query_id 检索ID
     * @return
     */
    @GetMapping("/get_previewCard_page_amount")
    public int getPreviewCardPageAmount(@RequestParam(required = true) int query_id) {
        return wxSearchService.getPreviewCardPageAmount(query_id);
    }






    /**
     *

     * @param page
     * @param query_id
     * @param sort              排序方式，可以为null，默认为null时，按昵称排序；    "FANS"按粉丝数量排序
     * @param search            搜索内容，不可为null;
     * @param follower          关注者uid， 即当前用户uid
     * @date 2022/4/17 10:22
     * @return java.util.List<main.biggreenbook.entity.vo.UserCard>
     */
    @GetMapping("/get_userCard")
    public List<UserCard> getUserCardsBySearch(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id,String sort,String search,String follower){
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getUserCardsPageAmount(query_id)) page = getUserCardsPageAmount(query_id) - 1;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",PAGESIZE);
        map.put("amount",query_id % PAGESIZE);
        map.put("sort",sort);
        map.put("search",search);
        map.put("follower",follower);

        //to service
        return wxSearchService.getUserCardsBySearch(query_id,map);
    }

    @GetMapping("/get_userCard_query_id")
    public int getSearchQueryId(String search) {
        return wxSearchService.getUserCardQueryId(search);
    }

    /**
     * 获取一共有多少页
     * @param query_id 检索ID
     * @return
     */
    @GetMapping("/get_userCard_page_amount")
    public int getUserCardsPageAmount(@RequestParam(required = true) int query_id) {
        return wxSearchService.getUserCardPageAmount(query_id);
    }



    private static final int PAGESIZE = 8;
}
