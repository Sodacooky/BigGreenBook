package main.biggreenbook.entity.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MessageMapper {

    public int setMessage(Map<?, ?> map);

    public int sendAllUser(Map<?, ?> map);

    public int getAllUid();
}
