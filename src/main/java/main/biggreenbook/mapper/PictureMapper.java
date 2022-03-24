package main.biggreenbook.mapper;

import main.biggreenbook.pojo.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {

    List<Picture> getPictureBySid(String sid);
}
