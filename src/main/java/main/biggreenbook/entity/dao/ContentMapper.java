package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.vo.PreviewCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContentMapper {

    /***
     * 获取当前的查询Id，实际上就是这一时刻的数据库数量
     * @return query_id
     */
    int getQueryId(String search);

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

    /**
     *

     * @param sort 排序条件：LAST:最新 / HOT:最热
     * @param search 搜索内容
     * @return PreviewCard
     */
    List<PreviewCard> queryContent(@Param("sort") String sort,@Param("search") String search,
                                   @Param("pageNum") int pageNum, @Param("pageSize") int pageSize,
                                   @Param("amount") int amount);

}
