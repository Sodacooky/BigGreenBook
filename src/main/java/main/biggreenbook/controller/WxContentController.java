package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//微信小程序内容相关控制器
@RestController
@CrossOrigin
@RequestMapping("/ctn")
public class WxContentController {

    @Autowired
    WxContentService wxContentService;

    /***
     * 获取首页瀑布流卡片
     * @param page 当前浏览页检索ID，必须带有query_id否则这个页是没有意义的
     * @param query_id 记住当前分页状态，避免重复
     * @return 预览页卡片们
     */
    @GetMapping("/get_home_page")
    public List<PreviewCard> getPreviewCards(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;
        //to service
        return wxContentService.getPreviewCards(page, query_id);
    }

    @GetMapping("/get_search")
    public List<PreviewCard> getPreviewCardsBySearch(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id, String search, String sort) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", 8);
        map.put("amount", query_id % 8);
        map.put("search", search);
        map.put("sort", sort);

        //to service
        return wxContentService.getPreviewCardsBySearch(query_id, map);
    }

    /***
     *  获取检索ID
     */
    @GetMapping("/get_home_query_id")
    public int getHomeQueryId() {
        return wxContentService.getQueryId(null);
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
}
