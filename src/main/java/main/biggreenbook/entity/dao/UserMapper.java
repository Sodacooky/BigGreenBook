package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.UserCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //通过uid获取用户的头像和昵称
    User getUserByUid(String uid);

    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId(String search);


    List<UserCard> getUserBySearch(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
                                   @Param("example") Example example);


}
