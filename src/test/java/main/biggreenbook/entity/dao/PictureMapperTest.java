package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Picture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PictureMapperTest {
    @Autowired
    PictureMapper pictureMapper;

    @Test
    public void getPictureBySid() {
        List<Picture> pictures = pictureMapper.getPictureBySid("1");
        pictures.forEach(System.out::println);
    }

    @Test
    public void getPictureBySidIndex() {
        System.out.println(pictureMapper.getPictureBySidIndex("1", 0));
    }
}
