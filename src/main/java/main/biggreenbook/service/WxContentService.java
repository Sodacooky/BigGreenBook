package main.biggreenbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.RedisHelper;
import main.biggreenbook.utils.StaticMappingHelper;
import main.biggreenbook.utils.UUIDGenerator;
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
     * 获取内容详情
     */
    public ContentInfo getContentInfo(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return null;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        ContentInfo contentInfo = contentMapper.getContentInfo(cid, uid);
        contentInfo.setUserAvatarPath(staticMappingHelper.doMapToDomain(contentInfo.getUserAvatarPath()));
        //路径映射
        switchJson(contentInfo);

        return contentInfo;
    }

    /**
     * 资源 路径映射
     *
     * @param contentInfo 详情页信息
     * @return void
     * @date 2022/4/20 21:25
     */
    private void switchJson(ContentInfo contentInfo) {
        //mapper
        ObjectMapper mapper = new ObjectMapper();
        //paths
        List<String> pathList = new ArrayList<>();
        try {
            pathList = mapper.readValue(contentInfo.getPath(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<String> pathListTemp = new ArrayList<>(pathList);
        pathList.clear();
        for (String path : pathListTemp) {
            pathList.add(staticMappingHelper.doMapToDomain(path));
        }
        contentInfo.setPaths(pathList);
        //tags
        List<String> tagList = new ArrayList<>();
        try {
            tagList = mapper.readValue(contentInfo.getTag(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        contentInfo.setTags(tagList);
    }


    // 内容互动 //
    // 内容互动 //
    // 内容互动 //

    /**
     * 添加点赞
     */
    public int giveLike(String goal_id, String customCode, String likeType) {
        if (!redisHelper.hasCustomCode(customCode))
            return contentMapper.queryLikeAmount(goal_id);

        String uid = redisHelper.getUidFromCustomCode(customCode);

        contentMapper.addLikes(goal_id, uid, likeType);
        contentMapper.updateLikeAmount(1, goal_id);

        return contentMapper.queryLikeAmount(goal_id);
    }

    /**
     * 取消点赞
     */
    public int ungiveLike(String goal_id, String customCode) {
        if (!redisHelper.hasCustomCode(customCode))
            return contentMapper.queryLikeAmount(goal_id);

        String uid = redisHelper.getUidFromCustomCode(customCode);

        contentMapper.subLikes(goal_id, uid);
        contentMapper.updateLikeAmount(-1, goal_id);

        return contentMapper.queryLikeAmount(goal_id);
    }

    /**
     * 添加收藏
     */
    public boolean addCollectionContent(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return false;

        String uid = redisHelper.getUidFromCustomCode(customCode);
        //添加收藏
        Timestamp date = new Timestamp(new Date().getTime());
        return contentMapper.addCollection(cid, uid, date) > 0;
    }

    /**
     * 取消收藏
     */
    public boolean deleteCollectionContent(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return false;

        String uid = redisHelper.getUidFromCustomCode(customCode);
        return contentMapper.deleteCollection(cid, uid) > 0;
    }

    /**
     * 举报内容
     */
    public boolean reportContent(String customCode, String cid, String reason) {
        if (!redisHelper.hasCustomCode(customCode)) return false;

        String uid = redisHelper.getUidFromCustomCode(customCode);

        Timestamp date = new Timestamp(new Date().getTime());
        return contentMapper.addReportContent(uid, cid, reason, date) > 0;
    }

    /**
     * 发布内容
     */
    public boolean publishContent(String customCode, Content content) {
        //登录判断
        if (!redisHelper.hasCustomCode(customCode)) return false;
        //设置内容的非用户编辑属性
        content.setCid(UUIDGenerator.generate());
        content.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        content.setLikeAmount(0);
        content.setUid(redisHelper.getUidFromCustomCode(customCode));
        //保存
        return contentMapper.publishContent(content) > 0;
    }

    /**
     * 修改发布的内容
     */
    public boolean updateContent(Content content) {
        return contentMapper.updateContent(content);
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

    @Autowired
    private RedisHelper redisHelper;


}
