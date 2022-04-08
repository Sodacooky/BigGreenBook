package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.vo.PreviewCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ContentMapper {

    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId();

    /**
     * 获取内容
     * 按最老在上顺序获取
     *
     * @param pageNum  按时间顺序的页，也就是用户期望的页的逆
     * @param pageSize 页容量
     * @return PreviewCards
     */
    List<PreviewCard> getContentByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取时间上最新的若干条，用于解决不完整分页
     *
     * @param amount 最新的若干条
     * @return PreviewCards
     */
    List<PreviewCard> getLatestContent(@Param("amount") int amount);


    List<ContentMessage> getContents(Map<?, ?> map);

    int countAllContents();

    int deleteSelect(List<?> list);

    List<ContentMessage> queryContents(Map<?, ?> map);

    // 计算符合查询结果的内容有多少条
    int countQueryContents(Map<?, ?> map);

    List<ContentMessage> queryContentsByUid(Map<?, ?> map);

    //int deleteContent(String cid);

    ContentMessage checkContent(Map<?, ?> map);
}
