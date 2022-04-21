package main.biggreenbook.service;

import main.biggreenbook.entity.dao.MessageMapper;
import main.biggreenbook.entity.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    // 给某个用户发消息
    public int setMessage(Map<?,?> map) {
        return messageMapper.setMessage(map);
    }

    // 给所有用户发消息
    public int sendAllUser(Map<?,?> map) {
        messageMapper.getAllUid();
        return messageMapper.sendAllUser(map);
    }

    // 获得某个用户收到的全部消息
    public List<Message> getMessage(Map<?, ?> map) {
        return messageMapper.getMessage(map);
    }

    // 用户删除收到的某条消息
    public int deleteMessage(Map<?, ?> map) {
        return messageMapper.deleteMessage(map);
    }

}
