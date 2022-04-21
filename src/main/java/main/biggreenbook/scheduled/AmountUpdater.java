package main.biggreenbook.scheduled;

import main.biggreenbook.entity.dao.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AmountUpdater {

    //更新内容的点赞数量，每分钟更新
    @Scheduled(cron = "0 */1 * * * *")
    public void updateContentLikeAmount() {

    }


    @Autowired
    ContentMapper contentMapper;

}
