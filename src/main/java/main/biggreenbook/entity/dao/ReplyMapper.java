package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Reply;
import main.biggreenbook.entity.vo.ReplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReplyMapper {

    //顾名思义
    List<ReplyVO> getAllTopReplyOfContent(@Param("cid") String cid);

    //顾名思义
    List<ReplyVO> getAllSubReply(@Param("rid") String rid);


    //写不写都一样
    int addReply(@Param("reply") Reply reply);

}
