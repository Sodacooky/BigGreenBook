package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ContentMapper;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.utils.UrlPathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxContentService {

    @Autowired
    ContentMapper contentMapper;

    @Autowired
    UrlPathMapper filePathMappingHelper;

    public List<PreviewCard> getPreviewCards(int page, int queryId) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && pageSize < queryId) {
            result.addAll(contentMapper.getLatestContent(queryId % pageSize));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < pageSize ? 1 : queryId / pageSize;
        int actualPage = pageAmount - 1 - page;
        result.addAll(contentMapper.getContentByPage(actualPage, pageSize));
        return result;
    }

    public int getQueryId() {
        return contentMapper.getQueryId();
    }

    private static final int pageSize = 8;
}
