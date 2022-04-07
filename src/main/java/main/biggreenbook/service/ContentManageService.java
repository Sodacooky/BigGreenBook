package main.biggreenbook.service;

import main.biggreenbook.entity.pojo.ContentMessage;

import java.util.List;
import java.util.Map;

public interface ContentManageService {
    List<ContentMessage> getContents(Map<?, ?> map);

    int countAllContents();

    int deleteSelect(List<?> list);

    List<ContentMessage> queryContents(Map<?,?> map);

    int countQueryContents(Map<?,?> map);

    List<ContentMessage> queryContentsByUid(Map<?,?> map);

    int deleteContent(String cid);

}
