package main.biggreenbook.service;

import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WxContentServiceTest {

    @Autowired
    WxContentService wxContentService;

    @Test
    public void getPreviewCardsTest() {
        int queryId = wxContentService.getQueryId(null);
        List<PreviewCard> previewCards = wxContentService.getPreviewCards(0,queryId);
        previewCards.forEach(System.out::println);
    }

    @Test
    public void getPreviewCards(){
        int page = 0;
        int pageSize = 8;
        String search = "";
        String sort = "HOT";
        int queryId = wxContentService.getQueryId(search);


        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",page);
        map.put("pageSize",pageSize);
        map.put("amount",queryId % pageSize);
        map.put("search",search);
        map.put("sort",sort);

        List<PreviewCard> cards = wxContentService.getPreviewCardsBySearch(queryId,map);
        cards.forEach(System.out::println);
    }

}
