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

    // 首页瀑布流 //
    // 首页瀑布流 //
    // 首页瀑布流 //

    /**
     * 获取当前首页瀑布流的QueryID，，实际上就是这一时刻的数据库数量
     *
     * @return query_id
     */
    int getHomePageQueryId();

    /**
     * 获取首页瀑布流的内容
     *
     * @param pageNum  页，按时间顺序，新内容在最后页
     * @param pageSize 页大小
     * @return 内容预览卡片
     */
    List<PreviewCard> getHomePageContent(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取首页瀑布流中，分页剩余的部分
     *
     * @param amount 最新的若干条
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getHomePageLatestPart(@Param("amount") int amount);

    // 搜索内容 //
    // 搜索内容 //
    // 搜索内容 //

    /**
     * 获取当前搜索的检索ID
     *
     * @param search 搜索的内容
     * @return 在当前搜索内容下的数量
     */
    int getSearchQueryId(String search);

    /**
     * @param map int pageNum    当前页数
     *            int pageSize   页面容量
     *            int amount     amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *            String search  搜索内容
     *            String sort    排序方式
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getContentBySearch(Map<String, Object> map);

    // 用户收藏夹，赞过的内容，自己发布的内容 //
    // 用户收藏夹，赞过的内容，自己发布的内容 //
    // 用户收藏夹，赞过的内容，自己发布的内容 //

    /**
     * 获取指定uid的用户的收藏夹的某一页内容的预览卡片
     * 按收藏的时间倒序，新收藏的在头部
     *
     * @param uid      user uid
     * @param pageNum  page index
     * @param pageSize page size
     * @return 预览卡片
     */
    List<PreviewCard> getUserCollections(@Param("uid") String uid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取用户的收藏夹收藏数
     *
     * @param uid user uid
     * @return collect amount
     */
    int getUserCollectionAmount(@Param("uid") String uid);

    /**
     * 获取用户赞过的内容
     *
     * @param uid      要获取的用户uid
     * @param pageNum  页
     * @param pageSize 页容量
     * @return 预览卡片
     */
    List<PreviewCard> getUserLiked(@Param("uid") String uid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取用户的赞过数量
     *
     * @param uid 要查看的用户
     * @return 数量
     */
    int getUserLikedAmount(@Param("uid") String uid);

    /**
     * 获取指定用户发布的内容
     *
     * @param uid      指定的用户uid
     * @param pageNum  页
     * @param pageSize 页容量
     * @return 内容预览卡片
     */
    List<PreviewCard> getUserPublished(@Param("uid") String uid, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取用户发布的内容的数量
     *
     * @param uid 指定的用户uid
     * @return 数量
     */
    int getUserPublishedAmount(@Param("uid") String uid);


    // 展示个人信息所需数据 //
    // 展示个人信息所需数据 //
    // 展示个人信息所需数据 //

    /**
     * 获取该用户发布的内容数量
     *
     * @param uid user uid
     * @return 数量
     */
    int getUserContentAmount(@Param("uid") String uid);


    // ################### 后台

    /**
     * @param cid 内容cid
     * @param uid 当前用户uid
     * @return main.biggreenbook.entity.vo.ContentInfo
     * @date 2022/4/16 7:01
     */
    ContentInfo getContentInfo(@Param("cid") String cid, @Param("uid") String uid);


    /**
     * 修改点赞数
     *
     * @param cid    内容cid
     * @param isLike 点赞数 +1 / -1
     * @return int
     * @date 2022/4/16 10:22
     */
    int updateLikeAmount(@Param("isLike") int isLike, @Param("cid") String cid);

    /**
     * 新增点赞
     *
     * @param likeType 点赞类型
     * @param goal     点赞目标
     * @param uid      点赞人uid
     * @return int
     * @date 2022/4/16 16:19
     */
    int addLikes(@Param("likeType") String likeType, @Param("goal") String goal, @Param("uid") String uid);

    /**
     * 取消点赞
     *
     * @param goal 点赞目标
     * @param uid  点赞人uid
     * @return int
     * @date 2022/4/16 16:19
     */
    int subLikes(@Param("goal") String goal, @Param("uid") String uid);

    /**
     * 查询内容的点赞数
     *
     * @param cid 内容cid
     * @return int 内容点赞数
     * @date 2022/4/16 10:24
     */
    int queryLikeAmount(String cid);

    /**
     * 添加收藏
     *
     * @param cid  收藏内容
     * @param uid  收藏者
     * @param date 收藏时间
     * @return int
     * @date 2022/4/16 19:27
     */
    int addCollection(@Param("cid") String cid, @Param("uid") String uid, @Param("date") Timestamp date);

    /**
     * 删除收藏
     *
     * @param cid 收藏内容
     * @param uid 收藏者
     * @return int
     * @date 2022/4/16 19:28
     */
    int deleteCollection(@Param("cid") String cid, @Param("uid") String uid);


    /**
     * @param uid    举报者uid
     * @param cid    内容cid
     * @param reason 举报原因
     * @param date   举报时间
     * @return int
     * @date 2022/4/16 18:24
     */
    int addReportContent(@Param("uid") String uid, @Param("cid") String cid, @Param("reason") String reason, @Param("date") Timestamp date);


    //什么玩意？
    List<ContentMessage> getContents(Map<?, ?> map);

    //获取Content数量？若是则与getQueryId()相等
    int countAllContents(Map<?, ?> map);

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

    List<ContentMessage> getNextContents(Map<?, ?> map);

    List<ContentMessage> getPreviousContents(Map<?, ?> map);

    /**
     * @param sort   排序条件：LAST:最新 / HOT:最热
     * @param search 搜索内容
     * @return PreviewCard
     */
    List<PreviewCard> getContentBySearch(@Param("sort") String sort, @Param("search") String search,
                                         @Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
                                         @Param("amount") int amount);

}
