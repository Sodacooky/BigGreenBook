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
        Map<String, Object> map = new HashMap<>();
        List<PreviewCard> contentByPage = contentMapper.getContentByPage(map);
        contentByPage.forEach(System.out::println);
    }

    @Test
    public void getLatestContentTest() {
        Map<String, Object> map = new HashMap<>();
        List<PreviewCard> latestContent = contentMapper.getLatestContent(map);
        latestContent.forEach(System.out::println);
    }

}
