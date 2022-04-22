package main.biggreenbook.entity.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CollectionMapper {


    //1 on exist
    int getUserCollectionState(@Param("uid") String uid, @Param("cid") String cid);

}
