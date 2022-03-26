package main.biggreenbook.mapper;

import main.biggreenbook.entity.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper mapper;

    @Test
    public void getUserByUid() {
        User user = mapper.getUserByUid("1");
        System.out.println(user);
    }
}
