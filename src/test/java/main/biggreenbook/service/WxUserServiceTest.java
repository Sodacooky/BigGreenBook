package main.biggreenbook.service;

import main.biggreenbook.entity.vo.Example;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WxUserServiceTest {
    @Autowired
    private WxUserService wxUserService;

    @Test
    public void getUserCards(){
        int queryId = wxUserService.getQueryId("布里");

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize",2);
        map.put("sort",null);
        map.put("search","布里");
        map.put("follower","1");
        map.put("amount",queryId % 2);

        List<UserCard> userCards = wxUserService.getUserCards(queryId,map);
        userCards.forEach(System.out::println);
    }
}
