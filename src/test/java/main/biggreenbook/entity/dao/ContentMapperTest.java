package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.pojo.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContentMapperTest {
    @Autowired
    ContentMapper mapper;

    @Test
    public void getContentByPage() {
        for (Content content : mapper.getContentByPage(0, 4)) {
            System.out.println(content);
        }
    }

    @Test
    public void getContentByCid() {
        Content contentByCid = mapper.getContentByCid("1");
        System.out.println(contentByCid);
    }
}
