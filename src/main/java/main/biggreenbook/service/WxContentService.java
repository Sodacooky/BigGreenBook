package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class WxContentService {

    //首页瀑布流

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
            Timestamp date = new Timestamp(System.currentTimeMillis());
            return contentMapper.addCollection(cid, uid, date);
        }
    }

    public int reportContent(String uid, String cid, String reason, Timestamp date) {

        return contentMapper.addReportContent(uid, cid, reason, date);
    }


    public int getQueryId(String search) {
        return contentMapper.getSearchQueryId(search);
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
