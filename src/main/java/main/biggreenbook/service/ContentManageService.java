package main.biggreenbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.pojo.ContentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentManageService {
    @Autowired
    private ContentMapper contentMapper;

    public List<ContentMessage> getContents(Map<?, ?> map) {
        List<ContentMessage> list = contentMapper.getContents(map);
        switchJson(list);
        return list;
    }

    public int countAllContents(Map<?, ?> map) {
        return contentMapper.countAllContents(map);
    }

    public int deleteSelect(List<?> list) {
        return contentMapper.deleteSelect(list);
    }

    public List<ContentMessage> queryContents(Map<?, ?> map) {
        List<ContentMessage> list = contentMapper.queryContents(map);
        switchJson(list);

        return list;
    }

    public int countQueryContents(Map<?, ?> map) {
        return contentMapper.countQueryContents(map);
    }

    public List<ContentMessage> getNextContents(Map<?, ?> map) {
        List<ContentMessage> list = contentMapper.getNextContents(map);
        switchJson(list);

        return list;
    }

    public List<ContentMessage> getPreviousContents(Map<?, ?> map) {
        List<ContentMessage> list = contentMapper.getPreviousContents(map);
        switchJson(list);

        return list;
    }

//    public int deleteContent(String cid) {
//        return contentMapper.deleteContent(cid);
//    }

    public ContentMessage checkContent(Map<?, ?> map) {
        ContentMessage contentMessage = contentMapper.checkContent(map);
        ObjectMapper mapper = new ObjectMapper();
        List<String> jsonList = new ArrayList<>();
        if (contentMessage != null) {
            try {
                jsonList = mapper.readValue(contentMessage.getPath(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));

            } catch (JsonProcessingException e) {

                e.printStackTrace();
            }
            contentMessage.setPaths(jsonList);
        }
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
            System.out.println(cm.getPaths().get(0));
        }
    }
}
