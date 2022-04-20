package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Follow;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.pojo.UserPrivacy;
import main.biggreenbook.entity.vo.UserCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    //注册用户
    int addUser(@Param("user") User user);

    //修改用户信息
    int updateUser(@Param("user") User user);


    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId(String search);

    /**
     * @param map int pageNum         页数
     *            int pageSize        页面容量
     *            int amount          amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *            String sort         排序方式；默认为null，即按nickname排序  FANS:粉丝数量
     *            String search       搜索内容
     *            String follower     关注者uid
     * @return java.util.List<main.biggreenbook.entity.vo.UserCard>
     */
    List<UserCard> getUserCardBySearch(Map<String, Object> map);


    // 个人信息相关 //

    //通过uid获取用户的头像和昵称
    User getUserByUid(String uid);

    /**
     * 获取两个用户之间的关注关系
     *
     * @param uid      被关注者
     * @param follower 被谁关注
     * @return 关注POJO类LIST，当没有关注时SIZE为0
     */
    List<Follow> getFollowStateBetween(@Param("uid") String uid, @Param("follower") String follower);

    /**
     * 进行关注
     *
     * @param me_uid   我
     * @param goal_uid 他
     * @param date     关注时间
     */
    void addFollow(@Param("me") String me_uid, @Param("goal") String goal_uid, @Param("date") Timestamp date);

    /**
     * 取关
     *
     * @param me_uid   我
     * @param goal_uid 他
     */
    void deleteFollow(@Param("me") String me_uid, @Param("goal") String goal_uid);

    /**
     * 获取某用户的关注者uid
     *
     * @param uid      用户uid
     * @param pageNum  页
     * @param pageSize 页大小
     * @return 用户uid
     */
    List<String> getFollowersUid(@Param("uid") String uid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取某用户的正在关注uid
     *
     * @param uid      用户uid
     * @param pageNum  页
     * @param pageSize 页大小
     * @return 用户uid
     */
    List<String> getFollowingsUid(@Param("uid") String uid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取某用户的关注者数量（粉丝数量）
     *
     * @param uid 用户uid
     * @return 数量
     */
    int getUserFollowersAmount(@Param("uid") String uid);

    /**
     * 获取某用户正在关注的数量
     *
     * @param uid 用户uid
     * @return 数量
     */
    int getUserFollowingsAmount(@Param("uid") String uid);

    /**
     * 为用户创建新的默认的隐私设定
     *
     * @param uid 要创建的用户的uid
     */
    void insertDefaultUserPrivacy(@Param("uid") String uid);

    /**
     * 获取用户的隐私设定
     *
     * @param uid 用户uid
     * @return 用户隐私设定
     */
    UserPrivacy getUserPrivacy(@Param("uid") String uid);

    /**
     * 更新用户的隐私设定
     *
     * @param new_privacy 新的隐私设定
     */
    void updateUserPrivacy(@Param("new_privacy") UserPrivacy new_privacy);
}
