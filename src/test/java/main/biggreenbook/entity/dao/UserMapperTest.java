package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.UserCard;
import main.biggreenbook.entity.vo.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper mapper;

    @Test
    public void getUserByUid() {
        User user = mapper.getUserByUid("1");
        System.out.println(user);
    }


    @Test
    public void getUserByPage(){
        Example example = new Example("FANS","布",3,"1");
        System.out.println(example);
        List<UserCard> userByPage = mapper.getUserBySearch(1, 2,example);
        userByPage.forEach(System.out::println);
    }
}
