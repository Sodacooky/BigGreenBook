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
        Map<String, Object> map = new HashMap<>();
        List<PreviewCard> previewCards = wxContentService.getPreviewCards(queryId,map);
        previewCards.forEach(System.out::println);
    }

    @Test
    public void getPreviewCards(){
        int queryId = wxContentService.getQueryId(null);

        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize", 8);
        map.put("sort","LATEST");
        map.put("search",null);
        map.put("amount",queryId % 8);

        List<PreviewCard> cards = wxContentService.getPreviewCards(queryId, map);

        cards.forEach(System.out::println);
    }

}
