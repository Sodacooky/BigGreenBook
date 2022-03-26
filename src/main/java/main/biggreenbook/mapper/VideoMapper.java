package main.biggreenbook.mapper;

import main.biggreenbook.entity.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VideoMapper {

    Video getVideoBySid(String sid);
}
