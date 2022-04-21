package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    public int setMessage(Map<?, ?> map);

    public int sendAllUser(Map<?, ?> map);

    public int getAllUid();

    public List<Message> getMessage(Map<?, ?> map);

    public int deleteMessage(Map<?, ?> map);

}
