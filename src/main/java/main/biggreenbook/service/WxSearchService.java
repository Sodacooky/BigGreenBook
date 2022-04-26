package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxSearchService {

    // 内容搜索 //
    // 内容搜索 //
    // 内容搜索 //

    public List<PreviewCard> doSearchContent(int queryId, String search, int page, String sort) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - page;
        //填充搜索数据
        //获取页头多余数据
        if (queryId % PAGESIZE != 0 && page == 0) {
            int amount = queryId % PAGESIZE;
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("amount", amount);
            paramsMap.put("pageNum", page);
            paramsMap.put("pageSize", PAGESIZE);
            paramsMap.put("search", search);
            paramsMap.put("sort", sort);
            result.addAll(contentMapper.getContentBySearch(paramsMap));
        }
        //获取页
        if (queryId >= PAGESIZE)    //若不足一页，该判断保证不重复取记录
        {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("pageNum", actualPage);
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

        //若按HOT方式排序，则按逆序排序，正序取记录，则排序紊乱，故在此再作一次排序
        //若按时间发布排序，因前端体现不出，故不需排序
        if (sort.equals("HOT")) {
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

    /**
     * 获取搜索的检索ID
     *
     * @param search 搜索的内容
     * @return 检索ID
     */
    public int getPreviewCardQueryId(String search) {
        return contentMapper.getSearchQueryId(search);
    }

    public int getPreviewCardPageAmount(int queryId) {
        //计算逆序页，获得数据
        if (queryId == 0)
            return 0;
        else if (queryId % PAGESIZE == 0)
            return queryId / PAGESIZE - 1;
        else
            return queryId < PAGESIZE ? 0 : queryId / PAGESIZE - 1;
    }


    @Autowired
    UserMapper userMapper;

    /**
     * @param queryId
     * @param map     int pageNum         页数
     *                int pageSize        页面容量
     *                int amount          amount != pageSize，则获取最新的若干条；否则为正常获取内容
     *                String sort         排序方式；默认为null，即按nickname排序  FANS:粉丝数量
     *                String search       搜索内容
     *                String follower     关注者uid
     * @return 用户搜索预览卡片
     */
    public List<UserCard> getUserCardsBySearch(int queryId, Map<String, Object> map) {
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if ((int) map.get("pageNum") == 0 && PAGESIZE < queryId) {
            result.addAll(userMapper.getUserCardBySearch(map));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - (int) map.get("pageNum");

        map.replace("amount", PAGESIZE);
        map.replace("pageNum", actualPage);

        result.addAll(userMapper.getUserCardBySearch(map));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
        });

        //按sort方式 逆序排序
        if (map.get("sort").equals("FANS")) {
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

    @Autowired
    StaticMappingHelper staticMappingHelper;

    @Autowired
    ContentMapper contentMapper;
}
