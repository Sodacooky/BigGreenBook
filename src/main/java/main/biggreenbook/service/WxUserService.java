package main.biggreenbook.service;

import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.Example;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.UrlPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UrlPathMapper filePathMappingHelper;

    public List<UserCard> getUserCards(int page, int queryId,Example example){
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && PAGESiZE < queryId) {
            result.addAll(userMapper.getUserBySearch(page,PAGESiZE,example));
        }

        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESiZE ? 1 : queryId / PAGESiZE;
        int actualPage = pageAmount - 1 - page;
        result.addAll(userMapper.getUserBySearch(actualPage,PAGESiZE,example));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(filePathMappingHelper.doMapToDomain(one.getUserAvatarPath()));
        });
        return result;
    }

    public int getQueryId(String search) {
        return userMapper.getQueryId(search);
    }

    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESiZE ? 1 : queryId / PAGESiZE;
    }

    private static final int PAGESiZE = 8;
}
