package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ContentMessage;
import main.biggreenbook.entity.vo.PreviewCard;
import org.apache.ibatis.annotations.Mapper;
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
    int getQueryId(String search);

    /**
     * 获取内容（含搜索）
     * 按最老在上顺序获取
     * @param map
     *        int pageNum 页数
     *        int pageSize 页面容量
     *        String sort 排序方式  默认按最新排序
     *        String search 搜索内容
     *        int amount 最新的若干条
     * @return PreviewCards
     */
    List<PreviewCard> getContentByPage(Map<String,Object> map);

    /**
     * 获取时间上最新的若干条，用于解决不完整分页（含搜索）
     * @param map
     *        int pageNum 页数
     *        int pageSize 页面容量
     *        String sort 排序方式；  LATEST:最新；HOT:最热
     *        String search 搜索内容
     *        int amount 最新的若干条
     * @return PreviewCards
     */
    List<PreviewCard> getLatestContent(Map<String,Object> map);

    //什么玩意？
    List<ContentMessage> getContents(Map<?, ?> map);

    //获取Content数量？若是则与getQueryId()相等
    int countAllContents();

    //删除所选的若干项，应该是传入cid
    int deleteSelect(List<?> list);

    //通过属性值，以一个或多个属性查找内容
    List<ContentMessage> queryContents(Map<?, ?> map);

    // 计算符合查询结果的内容有多少条
    int countQueryContents(Map<?, ?> map);

    //获取属于该用户的内容
    List<ContentMessage> queryContentsByUid(Map<?, ?> map);

    //int deleteContent(String cid);

    //似乎只是查找
    ContentMessage checkContent(Map<?, ?> map);

}
