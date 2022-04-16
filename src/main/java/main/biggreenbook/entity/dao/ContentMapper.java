package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.vo.PreviewCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ContentMapper {

    // ############## 首页瀑布流

    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId(String search);

    /**
     * @param pageNum  页数
     * @param pageSize 页面容量
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getContentByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * @param amount 最新的若干条
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getLatestContent(@Param("amount") int amount);

    /**
     * @param map int pageNum    当前页数
     *            int pageSize   页面容量
     *            int amount     amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *            String search  搜索内容
     *            String sort    排序方式
     * @return java.util.List<main.biggreenbook.entity.vo.PreviewCard>
     */
    List<PreviewCard> getContentBySearch(Map<String, Object> map);

    // ################### 用户收藏夹

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

    // ################### 后台

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
