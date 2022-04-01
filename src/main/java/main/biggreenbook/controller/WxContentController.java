package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return wxContentService.getPreviewCards(page, query_id);
    }

    /***
     *  获取检索ID
     */
    @GetMapping("/get_query_id")
    public int getHomeQueryId() {
        return wxContentService.getQueryId();
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
