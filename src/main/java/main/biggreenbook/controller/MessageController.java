package main.biggreenbook.controller;

import main.biggreenbook.entity.pojo.Message;
import main.biggreenbook.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 用户获得收到的全部消息
     *
     * @param uid 用户的uid
     * @return 返回该用户收到的全部消息
     * @deprecated use getMyMessages() instead
     */
    @Deprecated
    @GetMapping("/get")
    public List<Message> getMessage(@RequestParam("uid") String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        return messageService.getMessage(map);
    }


    /**
     * get all the message to the user(myself)
     *
     * @param customCode user custom login state string
     * @return the "message" list
     */
    @GetMapping("/get_my_msg")
    public List<Message> getMyMessages(@RequestParam("customCode") String customCode) {
        return messageService.getMyMessages(customCode);
    }

    /**
     * 删除用户选择的通知
     *
     * @param mid 用户要删除的通知的mid
     */
    @GetMapping("/delete/{mid}")
    public int getMessage(@PathVariable int mid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("mid", mid);

        return messageService.deleteMessage(map);
    }
}
