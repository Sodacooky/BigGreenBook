package main.biggreenbook.service;

import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.Example;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.StaticMappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WxUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StaticMappingHelper staticMappingHelper;

    public List<UserCard> getUserCards(int queryId, Map<String,Object> map) {
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if ((int)map.get("pageNum") == 0 && PAGESIZE < queryId) {
            result.addAll(userMapper.getUserCardByAmount(map));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - (int)map.get("pageNum");

        map.replace("pageNum",actualPage);
        result.addAll(userMapper.getUserCardBySearch(map));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
        });
        return result;
    }

    public int getQueryId(String search) {
        return userMapper.getQueryId(search);
    }

    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }

    private static final int PAGESIZE = 2;
}
