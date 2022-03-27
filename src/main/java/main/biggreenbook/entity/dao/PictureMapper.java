package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {

    List<Picture> getPictureBySid(String sid);

    Picture getPictureBySidIndex(String sid, int index);
}
