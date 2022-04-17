package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ContentMapper {

    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId(String search);

    /**
     *
     * @param pageNum    页数
     * @param pageSize   页面容量
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getContentByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     *
     * @param amount 最新的若干条
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getLatestContent(@Param("amount")int amount);

    /**
     * @param map
     *        int pageNum    当前页数
     *        int pageSize   页面容量
     *        int amount     amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *        String search  搜索内容
     *        String sort    排序方式
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getContentBySearch(Map<String,Object> map);

    /**
     *

     * @param cid           内容cid
     * @param uid           当前用户uid
     * @date 2022/4/16 7:01
     * @return main.biggreenbook.entity.vo.ContentInfo
     */
    ContentInfo getContentInfo(@Param("cid") String cid,@Param("uid") String uid);


    /**
     *
       修改点赞数
     * @param cid    内容cid
     * @param isLike   点赞数 +1 / -1
     * @date 2022/4/16 10:22
     * @return int
     */
    int updateLikeAmount(@Param("isLike") int isLike,@Param("cid") String cid);

    /**
     *
        新增点赞
     * @param likeType 点赞类型
     * @param goal     点赞目标
     * @param uid       点赞人uid
     * @date 2022/4/16 16:19
     * @return int
     */
    int addLikes(@Param("likeType")String likeType,@Param("goal")String goal,@Param("uid") String uid);

    /**
     *
        取消点赞
     * @param goal  点赞目标
     * @param uid   点赞人uid
     * @date 2022/4/16 16:19
     * @return int
     */
    int subLikes(@Param("goal")String goal,@Param("uid") String uid);

    /**
     *
       查询内容的点赞数
     * @param cid   内容cid
     * @date 2022/4/16 10:24
     * @return int 内容点赞数
     */
    int queryLikeAmount(String cid);

    /**
     *
        添加收藏
     * @param cid           收藏内容
     * @param uid           收藏者
     * @param date          收藏时间
     * @date 2022/4/16 19:27
     * @return int
     */
    int addCollection(@Param("cid") String cid,@Param("uid") String uid,@Param("date") Timestamp date);

    /**
     *
     * 删除收藏
     * @param cid           收藏内容
     * @param uid           收藏者
     * @date 2022/4/16 19:28
     * @return int
     */
    int deleteCollection(@Param("cid")String cid, @Param("uid")String uid);


    /**
     *

     * @param uid       举报者uid
     * @param cid       内容cid
     * @param reason    举报原因
     * @param date      举报时间
     * @date 2022/4/16 18:24
     * @return int
     */
    int addReportContent(@Param("uid") String uid,@Param("cid") String cid,@Param("reason") String reason,@Param("date") Timestamp date);


    //什么玩意？
    List<ContentMessage> getContents(Map<?, ?> map);

    //获取Content数量？若是则与getQueryId()相等
    int countAllContents();

    //删除所选的若干项，应该是传入cid
    int deleteSelect(List<?> list);

    //通过属性值，以一个或多个属性查找内容
    List<ContentMessage> queryContents(Map<?, ?> map);

    // 计算符合查询结果的内容有多少条
    int countQueryContents(Map<?, ?> map);

    //获取属于该用户的内容
    List<ContentMessage> queryContentsByUid(Map<?, ?> map);

    //int deleteContent(String cid);

    //似乎只是查找
    ContentMessage checkContent(Map<?, ?> map);



}
