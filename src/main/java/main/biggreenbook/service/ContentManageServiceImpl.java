package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentManageMapper;
import main.biggreenbook.entity.pojo.ContentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentManageServiceImpl implements ContentManageService{
    @Autowired
    private ContentManageMapper contentManageMapper;

    public void setContentManageMapper(ContentManageMapper contentManageMapper) {
        this.contentManageMapper = contentManageMapper;
    }

    @Override
    public List<ContentMessage> getContents(Map<?, ?> map) {
        return contentManageMapper.getContents(map);
    }

    @Override
    public int countAllContents() {
        return contentManageMapper.countAllContents();
    }

    @Override
    public int deleteSelect(List<?> list) {
        return contentManageMapper.deleteSelect(list);
    }

    @Override
    public List<ContentMessage> queryContents(Map<?,?> map) {
        return contentManageMapper.queryContents(map);
    }

    @Override
    public int countQueryContents(Map<?, ?> map) {
        return contentManageMapper.countQueryContents(map);
    }

    @Override
    public List<ContentMessage> queryContentsByUid(Map<?, ?> map) {
        return contentManageMapper.queryContentsByUid(map);
    }

    @Override
    public int deleteContent(String cid) {
        return contentManageMapper.deleteContent(cid);
    }
}
