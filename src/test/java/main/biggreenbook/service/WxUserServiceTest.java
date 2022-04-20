package main.biggreenbook.service;

import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.PreviewCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WxUserServiceTest {

    //login无法独立测试且已经测试通过！

    //check custom code 无法稳定测试！
    //与customCode相关测试均忽略

    @Test
    void getInfoTest() {
        User info = wxUserService.getInfo("1");
        System.out.println(info.toString());
    }

    @Test
    void getCollectionsTest() {
        List<PreviewCard> collections = wxUserService.getCollections("1", 0);
        collections.forEach(System.out::println);
    }
    
    @Test
    void getCollectionsPageAmountTest() {
        System.out.println(wxUserService.getCollectionPageAmount("1"));
    }

    @Autowired
    private WxUserService wxUserService;
}
