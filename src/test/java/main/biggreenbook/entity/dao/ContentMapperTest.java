package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ContentMapperTest {

    @Autowired
    ContentMapper contentMapper;

    @Test
    public void getQueryIdTest() {
        System.out.println("query_id=" + contentMapper.getQueryId());
    }

    @Test
    public void getContentByPageTest() {
        List<PreviewCard> contentByPage = contentMapper.getContentByPage(0, 8);
        contentByPage.forEach(System.out::println);
    }

    @Test
    public void getLatestContentTest() {
        List<PreviewCard> latestContent = contentMapper.getLatestContent(2);
        latestContent.forEach(System.out::println);
    }
}
