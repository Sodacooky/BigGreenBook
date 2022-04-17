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
import java.util.Map;

@Service
public class WxContentService {

    @Autowired
    ContentMapper contentMapper;

    @Autowired
    StaticMappingHelper staticMappingHelper;

    public List<PreviewCard> getPreviewCards(int page,int queryId) {
        ArrayList<PreviewCard> result = new ArrayList<>();
//        //判断query_id合法性
//        int latestQueryId = contentMapper.getQueryId();
//        if (queryId > latestQueryId) {
//            queryId = latestQueryId;
//        }
        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && PAGESIZE < queryId) {
            result.addAll(contentMapper.getLatestContent(queryId % PAGESIZE));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - page;

        result.addAll(contentMapper.getContentByPage(actualPage,PAGESIZE));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });

        return result;
    }

    public ContentInfo getContentInfo(String cid,String uid) {
        ContentInfo contentInfo = contentMapper.getContentInfo(cid,uid);

        //路径映射：图片组 未实现路径映射
        contentInfo.setUserAvatarPath(staticMappingHelper.doMapToDomain(contentInfo.getUserAvatarPath()));
        contentInfo.setPaths(staticMappingHelper.doMapToDomain(contentInfo.getPaths()));

        return contentInfo;
    }

    public int giveLike(int isLike,String likeType,String goal,String uid) {
        if (isLike == 0){
            //取消点赞，点赞数减少
            contentMapper.subLikes(goal,uid);
            contentMapper.updateLikeAmount(-1,goal);
        }else {
            //点赞，点赞数增加
            contentMapper.addLikes(likeType,goal,uid);
            contentMapper.updateLikeAmount(1,goal);
        }

        return contentMapper.queryLikeAmount(goal);
    }

    public int collectionContent(int isCollection, String cid, String uid) {
        if (isCollection == 0){
            //取消收藏
            return contentMapper.deleteCollection(cid,uid);
        }else {
            //添加收藏
            Timestamp date= new Timestamp(System.currentTimeMillis());
            return contentMapper.addCollection(cid,uid,date);
        }
    }

    public int reportContent(String uid, String cid, String reason, Timestamp date) {

        return contentMapper.addReportContent(uid,cid,reason,date);
    }


    public int getQueryId(String search) {
        return contentMapper.getQueryId(search);
    }

    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }

    //默认获取8个卡片
    private static final int PAGESIZE = 8;

}
