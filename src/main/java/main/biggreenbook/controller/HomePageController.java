package main.biggreenbook.controller;

import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页相关控制器
 */
@RestController
public class HomePageController {

    @Autowired
    HomePageService homePageService;

    @GetMapping("/home")
    public List<PreviewCard> getPreviewCards(@RequestParam int page) {
        //page parameter check
        if (page < 1) page = 1;
        //to service
        return homePageService.getPreviewCards(page - 1);
    }
}
