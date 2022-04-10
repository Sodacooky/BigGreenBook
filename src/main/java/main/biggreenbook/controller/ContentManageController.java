package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.vo.ManageContentPage;
import main.biggreenbook.service.ContentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/manage")
public class ContentManageController {
    @Autowired
    private ContentManageService contentManageService;

    @GetMapping(value = "/getContents/{pageIndex}")
    public ManageContentPage getContents(@PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Integer> map = new HashMap<>();
        map.put("index", index);
        map.put("target", target);
        List<ContentMessage> list = contentManageService.getContents(map);
        int totalContents = contentManageService.countAllContents();

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/deleteSelect/{select}")
    public int deleteSelect(@PathVariable List<Integer> select) {
        return contentManageService.deleteSelect(select);
    }

    @GetMapping(value = "/queryContents/{inputName}/{pageIndex}")
    public ManageContentPage queryConTents(@PathVariable String inputName, @PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("title", inputName);
        map.put("index", index);
        map.put("target", target);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/queryUid/{uid}/{pageIndex}")
    public ManageContentPage queryContentsByUid(@PathVariable String uid, @PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("index", index);
        map.put("target", target);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/queryNickname/{nickname}/{pageIndex}")
    public ManageContentPage queryContentsByNickname(@PathVariable String nickname, @PathVariable int pageIndex) {
        int index;
        int target;
        if (pageIndex == 1) {
            index = 0;
        } else {
            index = (pageIndex - 1) * 8;
        }
        target = index + 8;
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);
        map.put("index", index);
        map.put("target", target);
        List<ContentMessage> list = contentManageService.queryContents(map);
        int totalContents = contentManageService.countQueryContents(map);

        return new ManageContentPage(list, totalContents);
    }

    @GetMapping(value = "/check/{cid}")
    public ContentMessage checkContent(@PathVariable String cid) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);

        return contentManageService.checkContent(map);
    }

}
