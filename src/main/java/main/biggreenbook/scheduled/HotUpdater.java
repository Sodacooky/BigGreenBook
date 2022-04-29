package main.biggreenbook.scheduled;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.utils.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class HotUpdater {

    @Scheduled(cron = "0 */1 * * * *")
    public void updateHot() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("started to update hot ...");
        doUpdateHot();
        logger.info("finished updating hot.");
    }


    private void doUpdateHot() {
        //从数据库中获取最近两天的内容
        //获取两天前的时间
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, -2);
        //获取
        List<Content> contentToday = contentMapper.getContentAfterDate(new Timestamp(date.getTimeInMillis()));
        //排序，找出最热门的10个
        List<String> topTenCid = new ArrayList<>();
        contentToday.sort((o1, o2) -> -1 * Integer.compare(o1.getLikeAmount(), o2.getLikeAmount()));
        for (int index = 0; index != 10 && index < contentToday.size(); index++) {
            topTenCid.add(contentToday.get(index).getCid());
        }
        //将新的排行榜存入redis
        for (int i = 0; i != 10; i++) {
            if (i >= topTenCid.size()) redisHelper.setHotTop(i, "");
            else redisHelper.setHotTop(i, topTenCid.get(i));
        }
    }

    @Autowired
    RedisHelper redisHelper;

    @Autowired
    ContentMapper contentMapper;

}
