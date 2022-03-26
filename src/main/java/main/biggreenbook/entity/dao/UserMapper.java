package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    //通过uid获取用户的头像和昵称
    User getUserByUid(String uid);
}
