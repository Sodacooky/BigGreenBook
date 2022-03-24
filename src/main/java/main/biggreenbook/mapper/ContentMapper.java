package main.biggreenbook.mapper;

import main.biggreenbook.pojo.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContentMapper {

    /**
     * 分页获取内容（cid、标题、资源sid和点赞数)
     * 按最新获取，倒序
     * @param pageNum   当前页
     * @param pageSize  页容量
     */
    List<Content> getContentByPage(@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);

    Content getContentBycid(String cid);

}
