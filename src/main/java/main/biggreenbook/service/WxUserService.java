package main.biggreenbook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.vo.Example;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.utils.StaticMappingHelper;
import main.biggreenbook.utils.WxInfoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class WxUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StaticMappingHelper staticMappingHelper;

    public List<UserCard> getUserCards(int page, int queryId, Example example) {
        ArrayList<UserCard> result = new ArrayList<>();

        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && PAGESiZE < queryId) {
            result.addAll(userMapper.getUserBySearch(page, PAGESiZE, example));
        }

        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESiZE ? 1 : queryId / PAGESiZE;
        int actualPage = pageAmount - 1 - page;
        result.addAll(userMapper.getUserBySearch(actualPage, PAGESiZE, example));

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
        return queryId < PAGESiZE ? 1 : queryId / PAGESiZE;
    }

    /**
     * 处理登录
     *
     * @param jsCode 前端登录的Code
     * @return 自定义登录态（自定义登录状态ID）
     */
    public String login(String jsCode) {
        //fill value
        String appId = wxInfoContainer.getAppId();
        String secret = wxInfoContainer.getSecret();
        String wxLoginApiURL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&";
        String withParams = String.format("appid=%s&secret=%s&js_code=%s", appId, secret, jsCode);
        //do get
        try {
            JsonNode jsonNode = new ObjectMapper().readValue(new URL(wxLoginApiURL + withParams), JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//todo 返回自定义登录态
    }


    //默认获取8个卡片
    private static final int PAGESiZE = 8;

    @Autowired
    private WxInfoContainer wxInfoContainer;
}
