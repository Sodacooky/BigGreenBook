package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoMapperTest {
    @Autowired
    VideoMapper videoMapper;

    @Test
    public void getVideoBySid() {
        Video video = videoMapper.getVideoBySid("1");

        System.out.println(video);
    }
}
