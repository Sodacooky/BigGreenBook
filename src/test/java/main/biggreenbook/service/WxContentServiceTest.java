package main.biggreenbook.service;

import main.biggreenbook.entity.vo.ContentInfo;
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
        int queryId = wxContentService.getSearchQueryId(null);
        List<PreviewCard> previewCards = wxContentService.getHomePageContent(0, queryId);
        previewCards.forEach(System.out::println);
    }

    @Test
    public void getContentInfoTest() {
        ContentInfo contentInfo = wxContentService.getContentInfo("1", "1");
        System.out.println(contentInfo.getPaths());
        System.out.println(contentInfo);
    }

    @Test
    public void giveLikeTest() {
        //取消点赞
        //System.out.println(wxContentService.giveLike(0, null, "1", "1"));
        //点赞
        //System.out.println(wxContentService.giveLike(1, "content", "1", "1"));
    }

    @Test
    public void collectionContentTest() {
        //取消收藏
        //wxContentService.collectionContent(0, "1", "1");
        //添加收藏
        //wxContentService.collectionContent(1, "1", "1");
    }
}
