package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.PictureMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.dao.VideoMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.UrlPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxContentService {

    @Autowired
    ContentMapper contentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UrlPathMapper filePathMappingHelper;

    public List<PreviewCard> getPreviewCards(int page) {
        
        return null;
    }
}
