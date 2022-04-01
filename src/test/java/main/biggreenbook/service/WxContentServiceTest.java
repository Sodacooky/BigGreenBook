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
        List<PreviewCard> previewCards = wxContentService.getPreviewCards(0);
        previewCards.forEach(System.out::println);
    }
}
