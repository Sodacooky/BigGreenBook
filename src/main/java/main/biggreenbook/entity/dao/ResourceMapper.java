package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

// 只处理和Resource表相关的事情
@Mapper
@Repository
public interface ResourceMapper {

    //插入新的空资源，默认调用时已经保证资源ID唯一
    void insertNewEmpty(@Param("sid") String sid, @Param("type") String type);

    //获取资源
    Resource getBySid(@Param("sid") String sid);

    //更新资源
    int update(@Param("nres") Resource newResource);

}
