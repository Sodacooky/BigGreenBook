package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ContentMapperTest {

    @Autowired
    ContentMapper contentMapper;

    @Test
    public void getQueryIdTest() {
        System.out.println("query_id=" + contentMapper.getQueryId(null));
    }

    @Test
    public void getContentByPageTest() {
        List<PreviewCard> contentByPage = contentMapper.getContentByPage(0,8);
        contentByPage.forEach(System.out::println);
    }

    @Test
    public void getLatestContentTest() {
        List<PreviewCard> latestContent = contentMapper.getLatestContent(2);
        latestContent.forEach(System.out::println);
    }

    @Test
    public void getContentBySearchTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize",8);
        map.put("sort","HOT");

        //map.put("amount",2);
        map.put("amount",8);
        List<PreviewCard> cards = contentMapper.getContentBySearch(map);

        cards.forEach(System.out::println);
    }

}
