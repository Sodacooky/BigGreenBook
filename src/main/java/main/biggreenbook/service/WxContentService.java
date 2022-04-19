package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class WxContentService {

    // 首页瀑布流 //
    // 首页瀑布流 //
    // 首页瀑布流 //

    /**
     * 获取首页瀑布流检索ID（这一刻数据库中Content的数量）
     *
     * @return 检索ID
     */
    public int getHomePageQueryId() {
        return contentMapper.getHomePageQueryId();
    }

    /**
     * 获取首页瀑布流内容
     *
     * @param page    页
     * @param queryId 首页瀑布流检索ID
     * @return 内容预览卡数组
     */
    public List<PreviewCard> getHomePageContent(int page, int queryId) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && PAGESIZE < queryId) {
            result.addAll(contentMapper.getHomePageLatestPart(queryId % PAGESIZE));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - page;

        result.addAll(contentMapper.getHomePageContent(actualPage, PAGESIZE));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });

        return result;
    }

    // 搜索 //
    // 搜索 //
    // 搜索 //

    /**
     * 获取搜索的检索ID
     *
     * @param search 当前搜索内容
     * @return 检索ID
     */
    public int getSearchQueryId(String search) {
        return contentMapper.getSearchQueryId(search);
    }

    /**
     * @param page     当前页数
     * @param query_id 检索ID
     * @param search   搜索内容
     * @param sort     排序方式，LATEST/HOT
     * @return 内容预览卡片
     */
    public List<PreviewCard> getSearchContent(int page, int query_id, String search, String sort) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //计算逆序页，获得数据
        int pageAmount = query_id < PAGESIZE ? 1 : query_id / PAGESIZE;
        int actualPage = pageAmount - 1 - page;
        //填充搜索数据
        //获取页头多余数据
        if (query_id % PAGESIZE != 0 && page == 0) {
            int amount = query_id % PAGESIZE;
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("amount", amount);
            paramsMap.put("pageNum", page);
            paramsMap.put("pageSize", PAGESIZE);
            paramsMap.put("search", search);
            paramsMap.put("sort", sort);
            result.addAll(contentMapper.getContentBySearch(paramsMap));
        }
        //获取页
        {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("pageNum", page);
            paramsMap.put("pageSize", PAGESIZE);
            paramsMap.put("amount", PAGESIZE);
            paramsMap.put("search", search);
            paramsMap.put("sort", sort);
            result.addAll(contentMapper.getContentBySearch(paramsMap));
        }
        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });
        //
        return result;
    }


    // 内容详情 //
    // 内容详情 //
    // 内容详情 //

    /**
     * @param cid
     * @param uid
     * @return
     */
    public ContentInfo getContentInfo(String cid, String uid) {
        ContentInfo contentInfo = contentMapper.getContentInfo(cid, uid);

        //路径映射：图片组 未实现路径映射
        contentInfo.setUserAvatarPath(staticMappingHelper.doMapToDomain(contentInfo.getUserAvatarPath()));
        contentInfo.setPaths(staticMappingHelper.doMapToDomain(contentInfo.getPaths()));

        return contentInfo;
    }

    public int giveLike(int isLike, String likeType, String goal, String uid) {
        if (isLike == 0) {
            //取消点赞，点赞数减少
            contentMapper.subLikes(goal, uid);
            contentMapper.updateLikeAmount(-1, goal);
        } else {
            //点赞，点赞数增加
            contentMapper.addLikes(likeType, goal, uid);
            contentMapper.updateLikeAmount(1, goal);
        }

        return contentMapper.queryLikeAmount(goal);
    }

    public int collectionContent(int isCollection, String cid, String uid) {
        if (isCollection == 0) {
            //取消收藏
            return contentMapper.deleteCollection(cid, uid);
        } else {
            //添加收藏
            Timestamp date = new Timestamp(new Date().getTime());
            return contentMapper.addCollection(cid, uid, date);
        }
    }

    public int reportContent(String uid, String cid, String reason) {
        Timestamp date = new Timestamp(new Date().getTime());
        return contentMapper.addReportContent(uid, cid, reason, date);
    }


    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }

    //默认获取8个卡片
    private static final int PAGESIZE = 8;

    @Autowired
    ContentMapper contentMapper;

    @Autowired
    StaticMappingHelper staticMappingHelper;
}
