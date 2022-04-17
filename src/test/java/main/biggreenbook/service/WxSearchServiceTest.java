package main.biggreenbook.service;

import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WxSearchServiceTest {

    @Autowired
    WxSearchService wxSearchService;

    @Test
    public void getPreviewCardsBySearch(){
        int query_id = wxSearchService.getPreviewCardQueryId(null);
        int page = 0;
        int PAGESIZE = 8;
        String sort = "HOT";
        String search = null;
        String follower = "1";

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",PAGESIZE);
        map.put("amount",query_id % PAGESIZE);
        map.put("sort",sort);
        map.put("search",search);
        map.put("follower",follower);

        List<PreviewCard> cards = wxSearchService.getPreviewCardsBySearch(query_id, map);
        cards.forEach(System.out::println);
    }


    @Test
    public void getUserCardsTest(){}
}
