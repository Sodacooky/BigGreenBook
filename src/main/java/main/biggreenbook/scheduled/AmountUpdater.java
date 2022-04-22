package main.biggreenbook.scheduled;

import main.biggreenbook.entity.dao.ContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AmountUpdater {

    //更新内容的点赞数量，计划任务
    @Scheduled(cron = "0 */30 * * * *")
    public void updateContentLikeAmount() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("started to update content like amount...");
        doUpdateContentLikeAmount();
        logger.info("finished updating content like amount.");
    }

    //遍历content
    public void doUpdateContentLikeAmount() {
        contentMapper.updateAllLikeAmount();
    }

    @Autowired
    ContentMapper contentMapper;

}
