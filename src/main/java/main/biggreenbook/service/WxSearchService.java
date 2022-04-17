package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class WxSearchService {
    @Autowired
    StaticMappingHelper staticMappingHelper;


    @Autowired
    ContentMapper contentMapper;

    public List<PreviewCard> getPreviewCardsBySearch(int queryId, Map<String,Object> map){
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

        //按sort方式，逆序排序
        if (map.get("sort").equals("HOT")) {
            Comparator<PreviewCard> comparator = new Comparator<PreviewCard>() {
                @Override
                public int compare(PreviewCard p1, PreviewCard p2) {
                    return p2.getContentLikeAmount() - p1.getContentLikeAmount();
                }
            };
            result.sort(comparator);
        }

        return result;
    }

    public int getPreviewCardQueryId(String search) {
        return contentMapper.getQueryId(search);
    }

    public int getPreviewCardPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }






    @Autowired
    UserMapper userMapper;

    public List<UserCard> getUserCardsBySearch(int queryId, Map<String,Object> map) {
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if ((int)map.get("pageNum") == 0 && PAGESIZE < queryId) {
            result.addAll(userMapper.getUserCardBySearch(map));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - (int)map.get("pageNum");

        map.replace("amount",PAGESIZE);
        map.replace("pageNum",actualPage);

        result.addAll(userMapper.getUserCardBySearch(map));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
        });

        //按sort方式 逆序排序
        if (map.get("sort").equals("FANS")){
            Comparator<UserCard> comparator = new Comparator<UserCard>() {
                @Override
                public int compare(UserCard p1, UserCard p2) {
                    return p2.getFansAmount() - p1.getFansAmount();
                }
            };
            result.sort(comparator);
        }

        return result;
    }

    public int getUserCardQueryId(String search) {
        return userMapper.getQueryId(search);
    }

    public int getUserCardPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }


    //默认获取8个卡片
    private static final int PAGESIZE = 8;
}
