package main.biggreenbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import main.biggreenbook.entity.dao.ContentManageMapper;
import main.biggreenbook.entity.pojo.ContentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentManageService{
    @Autowired
    private ContentManageMapper contentManageMapper;

    public void setContentManageMapper(ContentManageMapper contentManageMapper) {
        this.contentManageMapper = contentManageMapper;
    }


    public List<ContentMessage> getContents(Map<?, ?> map) {
        List<ContentMessage> list = contentManageMapper.getContents(map);
        switchJson(list);
        return list;
    }

    public int countAllContents() {
        return contentManageMapper.countAllContents();
    }

    public int deleteSelect(List<?> list) {
        return contentManageMapper.deleteSelect(list);
    }

    public List<ContentMessage> queryContents(Map<?,?> map) {
        List<ContentMessage> list = contentManageMapper.queryContents(map);
        switchJson(list);
        return contentManageMapper.queryContents(map);
    }

    public int countQueryContents(Map<?, ?> map) {
        return contentManageMapper.countQueryContents(map);
    }

    public List<ContentMessage> queryContentsByUid(Map<?, ?> map) {
        List<ContentMessage> list = contentManageMapper.queryContentsByUid(map);
        switchJson(list);
        return list;
    }

    public int deleteContent(String cid) {
        return contentManageMapper.deleteContent(cid);
    }

    public ContentMessage checkContent(Map<?,?> map) {
        ContentMessage contentMessage = contentManageMapper.checkContent(map);
        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonList = new ArrayList<>();
        try {
            jsonList = mapper.readValue(contentMessage.getPath(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        contentMessage.setPaths(jsonList);

        return contentMessage;
    }

    private void switchJson(List<ContentMessage> list) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonList = new ArrayList<>();
        for (ContentMessage cm : list) {
            try {
                jsonList = mapper.readValue(cm.getPath(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));

            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            cm.setPaths(jsonList);
        }
    }
}
