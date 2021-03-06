package main.biggreenbook.entity.dao;

import main.biggreenbook.controller.WxSearchController;
import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.service.WxSearchService;
import main.biggreenbook.utils.StaticMappingHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class ContentMapperTest {

    // 首页瀑布流 //
    // 首页瀑布流 //
    // 首页瀑布流 //

    @Test
    public void getHomePageQueryIdTest() {
        System.out.println(contentMapper.getHomePageQueryId());
    }

    @Test
    public void getHomePageContentTest() {
        List<PreviewCard> homePageContent = contentMapper.getHomePageContent(0, 8);
        homePageContent.forEach(System.out::println);
    }

    @Test
    public void getHomePageLatestPartTest() {
        List<PreviewCard> homePageContent = contentMapper.getHomePageLatestPart(2);
        homePageContent.forEach(System.out::println);
    }

    // 搜索内容 //
    // 搜索内容 //
    // 搜索内容 //

    @Test
    public void getSearchQueryIdTest() {
        System.out.println(contentMapper.getSearchQueryId("布里"));
    }

    @Test
    public void getContentBySearchTest() {
        int queryId = wxSearchController.getContentSearchQueryId(null);
        System.out.println(queryId);
        List<PreviewCard> contentBySearch = wxSearchController.doContentSearch(10, queryId, null, "HOT");
        contentBySearch.forEach(System.out::println);
    }
    @Test
    public void getUserCardBySearchTest(){
        int queryId = wxSearchController.getSearchQueryId(null);
        System.out.println(queryId);
        List<UserCard> userCards = wxSearchController.doUserSearch(10, queryId, null, "obrK14iCCFuFye29bq-dV7BJU9l4", "FANS");
        userCards.forEach(System.out::println);
    }


    // 用户收藏夹 //
    // 用户收藏夹 //
    // 用户收藏夹 //

    @Test
    public void getUserCollectionAmountTest() {
        System.out.println(contentMapper.getUserCollectionAmount("0"));
    }

    @Test
    public void getUserCollectionsTest() {
        List<PreviewCard> userCollections = contentMapper.getUserCollections("0", 0, 8);
        userCollections.forEach(System.out::println);
    }

    @Autowired
    WxSearchController wxSearchController;

    //contentMapper of the testing
    @Autowired
    ContentMapper contentMapper;

    @Autowired
    StaticMappingHelper staticMappingHelper;

    @Test
    public void getContentInfoTest() {
        ContentInfo contentInfo = contentMapper.getContentInfo("1", "1");
        System.out.println(contentInfo);
    }


    @Test
    public void updateLikeAmountTest() {
        contentMapper.updateLikeAmount(-1,"obrK14iCCFuFye29bq-dV7BJU9l4");
    }

    @Test
    public void addLikesTest() {
        contentMapper.addLikes("content", "obrK14iCCFuFye29bq-dV7BJU9l4", "obrK14iCCFuFye29bq-dV7BJU9l4",new Timestamp(new Date().getTime()));
    }

    @Test
    public void subLikesTest() {
        contentMapper.subLikes("obrK14iCCFuFye29bq-dV7BJU9l4", "obrK14iCCFuFye29bq-dV7BJU9l4");
    }

    @Test
    public void queryLikeAmountTest() {
        contentMapper.queryLikeAmount("obrK14iCCFuFye29bq-dV7BJU9l4");
    }

    @Test
    public void addReportContentTest() {
        Timestamp date = new Timestamp(new Date().getTime());
        contentMapper.addReportContent("obrK14iCCFuFye29bq-dV7BJU9l4", "obrK14iCCFuFye29bq-dV7BJU9l4", "不可奉告", date);
    }

    @Test
    public void addConllectionTest() {
        Timestamp date = new Timestamp(new Date().getTime());
        contentMapper.addCollection("obrK14iCCFuFye29bq-dV7BJU9l4", "obrK14iCCFuFye29bq-dV7BJU9l4", date);
    }

    @Test
    public void deleteCollectionTest() {
        contentMapper.deleteCollection("obrK14iCCFuFye29bq-dV7BJU9l4", "obrK14iCCFuFye29bq-dV7BJU9l4");
    }


    @Test
    public void publishContentTest(){
        Content content = new Content("obrK14iCCFuFye29bq-dV7BJU113","原神","xiangling","[\"原神\", \"香菱\", \"二次元\"]",new Timestamp(new Date().getTime()),"picture",0,"1","1");
        contentMapper.publishContent(content);
    }

    @Test
    public void updateContent(){
        Content content = new Content("obrK14iCCFuFye29bq-dV7BJU114","原神原神原神","xiangling","[\"原神\", \"香菱\", \"二次元\"]",new Timestamp(new Date().getTime()),"picture",0,"1","1");
        contentMapper.updateContent(content);
    }

}
