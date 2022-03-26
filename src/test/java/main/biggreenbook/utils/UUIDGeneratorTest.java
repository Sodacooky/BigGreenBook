package main.biggreenbook.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UUIDGeneratorTest {

    @Test
    void checkNoBar() {
        UUIDGenerator.generate().indexOf("-");
    }
}
