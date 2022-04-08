package main.biggreenbook.service;

import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WxContentServiceTest {

    @Autowired
    WxContentService wxContentService;

    @Test
    public void getPreviewCardsTest() {
        int queryId = wxContentService.getQueryId();
        List<PreviewCard> previewCards = wxContentService.getPreviewCards(0, queryId);
        previewCards.forEach(System.out::println);
    }
}
