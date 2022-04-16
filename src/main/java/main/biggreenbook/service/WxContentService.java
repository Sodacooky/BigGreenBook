package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<PreviewCard> getPreviewCardsBySearch(int queryId,Map<String,Object> map){
        ArrayList<PreviewCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if ( (int) map.get("pageNum") == 0 && PAGESIZE < queryId) {
            result.addAll(contentMapper.getContentBySearch(map));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - (int) map.get("pageNum");

        //amount == PAGESIZE 正常获取内容
        map.replace("amount",PAGESIZE);
        map.replace("pageNum",actualPage);

        result.addAll(contentMapper.getContentBySearch(map));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });

        return result;
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
