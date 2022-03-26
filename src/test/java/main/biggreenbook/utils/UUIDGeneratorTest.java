package main.biggreenbook.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UUIDGeneratorTest {
    @Test
    void checkNoBar() {
        int i = UUIDGenerator.generate().indexOf("-");
        Assertions.assertEquals(i, -1);
    }
}
