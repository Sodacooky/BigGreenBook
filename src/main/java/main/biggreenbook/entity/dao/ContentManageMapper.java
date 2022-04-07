package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ContentMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ContentManageMapper {
    public List<ContentMessage> getContents(Map<?, ?> map);

    public int countAllContents();

    public int deleteSelect(List<?> list);

    public List<ContentMessage> queryContents(Map<?,?> map);

    // 计算符合查询结果的内容有多少条
    public int countQueryContents(Map<?,?> map);

    public List<ContentMessage> queryContentsByUid(Map<?,?> map);

    public int deleteContent(String cid);
}
