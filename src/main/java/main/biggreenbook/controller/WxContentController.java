package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.WxContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//微信小程序内容相关控制器
@RestController
@CrossOrigin
public class WxContentController {

    @Autowired
    WxContentService wxContentService;

    /***
     * 获取首页瀑布流卡片
     * @param page 当前浏览页，必须带有query_id否则这个页是没有意义的
     * @param query_id 记住当前分页状态，避免重复
     * @return 预览页卡片们
     */
    @GetMapping("/home")
    public List<PreviewCard> getPreviewCards(@RequestParam(required = true) int page, @RequestParam(required = true) int query_id) {
        //page parameter check
        if (page < 1) page = 1;
        //to service
        return wxContentService.getPreviewCards(page - 1);
    }

    /***
     *  获取分页状态
     */
    @GetMapping("/get_home_query_id")
    public int getHomeQueryId() {
        return -1;//unimplemented
    }
}
