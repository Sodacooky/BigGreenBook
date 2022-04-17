package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.UserCard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     *
     * @param map
     *      int pageNum         页数
     *      int pageSize        页面容量
     *      int amount          amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *      String sort         排序方式；默认为null，即按nickname排序  FANS:粉丝数量
     *      String search       搜索内容
     *      String follower     关注者uid
     *
     * @date 2022/4/12 20:46
     * @return java.util.List<main.biggreenbook.entity.vo.UserCard>
     */
    List<UserCard> getUserCardBySearch(Map<String,Object> map);

}
