package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.dao.PictureMapper;
import main.biggreenbook.entity.dao.UserMapper;
import main.biggreenbook.entity.dao.VideoMapper;
import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.pojo.User;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.UrlPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomePageService {

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
        //page parameter check
        if (page < 0) page = 0;
        System.out.println("page: " + page);
        //list for controller
        ArrayList<PreviewCard> previewCards = new ArrayList<>();
        //get content information
        List<Content> contents = contentMapper.getContentByPage(page, 8);
        //use content information to get other resources
        contents.forEach(content -> {
            //temp preview card
            PreviewCard previewCard = new PreviewCard();
            //set content information
            previewCard.setContentTitle(content.getTitle());
            previewCard.setContentLikeAmount(content.getLikeAmount());
            previewCard.setResourceType(content.getType());
            if (content.getType() == 0) {
                //picture
                String originalFilePath = pictureMapper.getPictureBySidIndex(content.getSid(), 0).getPath();
                previewCard.setResourcePath(filePathMappingHelper.doMapToDomain(originalFilePath));
            } else if (content.getType() == 1) {
                //video
                String originalFilePath = videoMapper.getVideoBySid(content.getSid()).getPath();
                previewCard.setResourcePath(filePathMappingHelper.doMapToDomain(originalFilePath));
            }

            //get user information
            User userByUid = userMapper.getUserByUid(content.getUid());
            //set user information
            previewCard.setUserNickname(userByUid.getNickname());
            previewCard.setUserAvatarPath(userByUid.getAvatarPath());

            //put it into the list
            previewCards.add(previewCard);
        });
        return previewCards;
    }
}
