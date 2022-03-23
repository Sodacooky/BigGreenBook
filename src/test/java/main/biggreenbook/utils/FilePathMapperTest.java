package main.biggreenbook.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class FilePathMapperTest {

    @Test
    void picturePathTest() {
        String result = FilePathMapper.getPictureFilePath("a.jpg");
        System.out.println(result);
    }

    @Test
    void savePicture() throws IOException {
        String result = FilePathMapper.getPictureFilePath("a.jpg");
        File file = new File(result);
        file.createNewFile();
    }
}
