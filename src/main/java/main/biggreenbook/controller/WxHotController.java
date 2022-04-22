package main.biggreenbook.controller;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信热榜相关控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/hot")
public class WxHotController {

    @GetMapping("/get")
    public List<PreviewCard> get() {
        return getPreviewCardByCid(getHotContentIdFromRedis());
    }
    

    //
    private List<String> getHotContentIdFromRedis() {
        List<String> hotContentId = new ArrayList<>();
        for (int i = 0; i != 10; i++) {
            String cid = redisHelper.getHotTop(i);
            if (cid == null || cid.isEmpty()) break;
            hotContentId.add(cid);
        }
        return hotContentId;
    }

    //
    private List<PreviewCard> getPreviewCardByCid(List<String> cids) {
        List<PreviewCard> results = new ArrayList<>();
        cids.forEach(cid -> {
            results.add(contentMapper.getPreviewCardByCid(cid));
        });
        return results;
    }


    @Autowired
    ContentMapper contentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisHelper redisHelper;
}
