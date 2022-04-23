package main.biggreenbook.entity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LikeMapper {

    // 1 on liked
    int getLikeStateOfContent(@Param("uid") String uid, @Param("cid") String cid);

}
