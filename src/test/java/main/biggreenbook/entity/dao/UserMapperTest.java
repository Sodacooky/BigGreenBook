package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.UserCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void getUserCardBySearch(){

        /*      int pageNum 页数
         *      int pageSize 页面容量
         *      int amount 最新的若干条
         *      String sort 排序方式；  FANS:粉丝数量
         *      String search 搜索内容
         *      String follower 关注者uid
         *
         */
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",0);
        map.put("pageSize",6);
        map.put("sort","FANS");
        map.put("search",null);
        map.put("follower","1");
        map.put("amount",2);

        List<UserCard> userByPage = mapper.getUserCardBySearch(map);
        userByPage.forEach(System.out::println);
    }

    @Test
    public void addUserTest(){
        User user = new User("113","香菱","万民百味",1,new Date(new java.util.Date().getTime()),0,"avatar/xiangling.jpg");
        mapper.addUser(user);
    }

    @Test
    public void updateUserTest(){
        User user = new User("111","香菱","万民百味",1,new Date(new java.util.Date().getTime()),0,"avatar/xiangling.jpg");
        mapper.updateUser(user);

    }
}
