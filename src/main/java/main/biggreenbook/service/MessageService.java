package main.biggreenbook.service;

import main.biggreenbook.entity.dao.MessageMapper;
import main.biggreenbook.entity.pojo.Message;
import main.biggreenbook.utils.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    // 给某个用户发消息
    public int setMessage(Map<?, ?> map) {
        return messageMapper.setMessage(map);
    }

    // 给所有用户发消息
    public int sendAllUser(Map<?, ?> map) {
        messageMapper.getAllUid();
        return messageMapper.sendAllUser(map);
    }

    // 获得某个用户收到的全部消息

    /**
     * @param map
     * @return
     * @deprecated use getMyMessages() instead
     */
    @Deprecated
    public List<Message> getMessage(Map<?, ?> map) {
        return messageMapper.getMessage(map);
    }

    public List<Message> getMyMessages(String customCode) {
        //check customCode
        if (!redisHelper.hasCustomCode(customCode)) return new ArrayList<>();
        //fill sql params
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", redisHelper.getUidFromCustomCode(customCode));
        //do query
        return messageMapper.getMessage(params);
    }


    // 用户删除收到的某条消息
    public int deleteMessage(Map<?, ?> map) {
        return messageMapper.deleteMessage(map);
    }


    @Autowired
    private RedisHelper redisHelper;

}
