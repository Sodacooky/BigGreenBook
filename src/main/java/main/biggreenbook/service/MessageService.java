package main.biggreenbook.service;

import main.biggreenbook.entity.dao.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    public int setMessage(Map<?,?> map) {
        return messageMapper.setMessage(map);
    }

    public int sendAllUser(Map<?,?> map) {
        messageMapper.getAllUid();
        return messageMapper.sendAllUser(map);
    }

//    public int suspendMessage(Map<?, ?> map) {
//        return messageMapper.suspendMessage(map);
//    }
//
//    public int deleteContentMessage(Map<?, ?> map) {
//        return messageMapper.deleteContentMessage(map);
//    }
//
//    public int restoreMessage(Map<?, ?> map) {
//        return restoreMessage(map);
//    }
}
