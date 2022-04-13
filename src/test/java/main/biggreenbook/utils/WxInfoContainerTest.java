package main.biggreenbook.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WxInfoContainerTest {

    @Autowired
    private WxInfoContainer wxInfoContainer;

    @Test
    public void getAppIdTest() {
        System.out.println(wxInfoContainer.getAppId());
    }

    @Test
    public void getSecretTest() {
        System.out.println(wxInfoContainer.getSecret());
    }
}
