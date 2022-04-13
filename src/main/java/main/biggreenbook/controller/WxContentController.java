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
    public List<PreviewCard> getPreviewCards(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id,String sort,String search) {
        //page parameter check
        if (page < 0) page = 0;
        if (page >= getHomePageAmount(query_id)) page = getHomePageAmount(query_id) - 1;

        Map<String, Object> map = new HashMap<>();
        //封装参数：页数、排序方式、搜索内容、最新的若干条；    页面容量由Service层封装
        map.put("pageNum",page);
        map.put("sort",sort);
        map.put("search",search);
        map.put("amount",query_id % 8);

        //to service
        return wxContentService.getPreviewCards(query_id,map);
    }

    /***
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
