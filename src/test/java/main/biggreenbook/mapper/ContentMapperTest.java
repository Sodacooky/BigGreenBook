package main.biggreenbook.mapper;

import main.biggreenbook.entity.pojo.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ContentMapperTest {
    @Autowired
    ContentMapper mapper;

    @Test
    public void getContentByPage() {
        List<Content> contents = mapper.getContentByPage(0, 4);

        for (Content content : contents) {
            System.out.println(content);
        }
    }

    @Test
    public void getContentBycid() {
        Content contentBycid = mapper.getContentByCid("1");
        System.out.println(contentBycid);
    }
}
