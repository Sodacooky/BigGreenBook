package main.biggreenbook.service;

import main.biggreenbook.entity.vo.Example;
import main.biggreenbook.entity.vo.UserCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WxUserServiceTest {
    @Autowired
    private WxUserService wxUserService;

    @Test
    public void getUserCards(){
        int queryId = wxUserService.getQueryId(null);
        int page = 0;

        Example example = new Example();
        example.setAmount(queryId % 8);
        example.setSearch(null);
        example.setSort("FANS");
        example.setFollower("2");

        List<UserCard> userCards = wxUserService.getUserCards(page, queryId, example);

        userCards.forEach(System.out::println);
    }
}
