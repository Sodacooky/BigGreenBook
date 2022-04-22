package main.biggreenbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import main.biggreenbook.entity.dao.*;
import main.biggreenbook.entity.pojo.Content;
import main.biggreenbook.entity.pojo.Reply;
import main.biggreenbook.entity.pojo.Resource;
import main.biggreenbook.entity.vo.ContentInfo;
import main.biggreenbook.entity.vo.PreviewCard;
import main.biggreenbook.entity.vo.ReplyVO;
import main.biggreenbook.utils.RedisHelper;
import main.biggreenbook.utils.StaticMappingHelper;
import main.biggreenbook.utils.UUIDGenerator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class WxContentService {

    // 首页瀑布流 //
    // 首页瀑布流 //
    // 首页瀑布流 //

    /**
     * 获取首页瀑布流检索ID（这一刻数据库中Content的数量）
     *
     * @return 检索ID
     */
    public int getHomePageQueryId() {
        return contentMapper.getHomePageQueryId();
    }

    /**
     * 获取首页瀑布流内容
     *
     * @param page    页
     * @param queryId 首页瀑布流检索ID
     * @return 内容预览卡数组
     */
    public List<PreviewCard> getHomePageContent(int page, int queryId) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //如果是第一页，那么需要解决多余的部分数据
        if (page == 0 && PAGESIZE < queryId) {
            result.addAll(contentMapper.getHomePageLatestPart(queryId % PAGESIZE));
        }
        //计算逆序页，获得数据
        int pageAmount = queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
        int actualPage = pageAmount - 1 - page;

        result.addAll(contentMapper.getHomePageContent(actualPage, PAGESIZE));

        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });

        return result;
    }

    // 搜索 //
    // 搜索 //
    // 搜索 //

    /**
     * 获取搜索的检索ID
     *
     * @param search 当前搜索内容
     * @return 检索ID
     */
    public int getSearchQueryId(String search) {
        return contentMapper.getSearchQueryId(search);
    }

    /**
     * @param page     当前页数
     * @param query_id 检索ID
     * @param search   搜索内容
     * @param sort     排序方式，LATEST/HOT
     * @return 内容预览卡片
     */
    public List<PreviewCard> getSearchContent(int page, int query_id, String search, String sort) {
        ArrayList<PreviewCard> result = new ArrayList<>();
        //计算逆序页，获得数据
        int pageAmount = query_id < PAGESIZE ? 1 : query_id / PAGESIZE;
        int actualPage = pageAmount - 1 - page;
        //填充搜索数据
        //获取页头多余数据
        if (query_id % PAGESIZE != 0 && page == 0) {
            int amount = query_id % PAGESIZE;
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("amount", amount);
            paramsMap.put("pageNum", page);
            paramsMap.put("pageSize", PAGESIZE);
            paramsMap.put("search", search);
            paramsMap.put("sort", sort);
            result.addAll(contentMapper.getContentBySearch(paramsMap));
        }
        //获取页
        {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("pageNum", page);
            paramsMap.put("pageSize", PAGESIZE);
            paramsMap.put("amount", PAGESIZE);
            paramsMap.put("search", search);
            paramsMap.put("sort", sort);
            result.addAll(contentMapper.getContentBySearch(paramsMap));
        }
        //路径映射
        result.forEach(one -> {
            one.setUserAvatarPath(staticMappingHelper.doMapToDomain(one.getUserAvatarPath()));
            one.setResourcePath(staticMappingHelper.doMapToDomain(one.getResourcePath()));
        });
        //
        return result;
    }


    // 内容详情 //
    // 内容详情 //
    // 内容详情 //

    /**
     * 获取内容详情
     */
    public ContentInfo getContentInfo(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return null;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        ContentInfo contentInfo = contentMapper.getContentInfo(cid, uid);
        contentInfo.setUserAvatarPath(staticMappingHelper.doMapToDomain(contentInfo.getUserAvatarPath()));
        //路径映射
        switchJson(contentInfo);
        return contentInfo;
    }

    /**
     * 资源 路径映射
     *
     * @param contentInfo 详情页信息
     * @return void
     * @date 2022/4/20 21:25
     */
    private void switchJson(ContentInfo contentInfo) {
        //mapper
        ObjectMapper mapper = new ObjectMapper();
        //paths
        List<String> pathList = new ArrayList<>();
        try {
            pathList = mapper.readValue(contentInfo.getPath(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<String> pathListTemp = new ArrayList<>(pathList);
        pathList.clear();
        for (String path : pathListTemp) {
            pathList.add(staticMappingHelper.doMapToDomain(path));
        }
        contentInfo.setPaths(pathList);
        //tags
        List<String> tagList = new ArrayList<>();
        try {
            tagList = mapper.readValue(contentInfo.getTag(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        contentInfo.setTags(tagList);
    }


    // 内容互动 //
    // 内容互动 //
    // 内容互动 //

    /**
     * 添加点赞
     */
    public int giveLike(String goal_id, String customCode, String likeType) {
        if (!redisHelper.hasCustomCode(customCode))
            return contentMapper.getContentLikeAmount(goal_id);//没有错误反馈

        String uid = redisHelper.getUidFromCustomCode(customCode);

        contentMapper.addLikes(likeType, goal_id, uid, new Timestamp(new Date().getTime()));
        //contentMapper.updateLikeAmount(1, goal_id);
        contentMapper.updateSpecifiedLikeAmount(goal_id);

        return contentMapper.getContentLikeAmount(goal_id);
    }

    /**
     * 取消点赞
     */
    public int ungiveLike(String goal_id, String customCode) {
        if (!redisHelper.hasCustomCode(customCode))
            return contentMapper.getContentLikeAmount(goal_id);

        String uid = redisHelper.getUidFromCustomCode(customCode);

        contentMapper.subLikes(goal_id, uid);
        //contentMapper.updateLikeAmount(-1, goal_id);
        contentMapper.updateSpecifiedLikeAmount(goal_id);


        return contentMapper.getContentLikeAmount(goal_id);
    }

    /**
     * 添加收藏
     */
    public boolean addCollectionContent(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return false;
        //uid
        String uid = redisHelper.getUidFromCustomCode(customCode);
        //
        if (collectionMapper.getUserCollectionState(uid, cid) == 1) return false;

        //添加收藏
        return contentMapper.addCollection(cid, uid, new Timestamp(Calendar.getInstance().getTimeInMillis())) > 0;
    }

    /**
     * 取消收藏
     */
    public boolean deleteCollectionContent(String cid, String customCode) {
        if (!redisHelper.hasCustomCode(customCode)) return false;
        String uid = redisHelper.getUidFromCustomCode(customCode);
        if (collectionMapper.getUserCollectionState(uid, cid) == 0) return false;
        return contentMapper.deleteCollection(cid, uid) > 0;
    }

    /**
     * 举报内容
     */
    public boolean reportContent(String customCode, String cid, String reason) {
        //customCode
        if (!redisHelper.hasCustomCode(customCode)) return false;
        //uid
        String uid = redisHelper.getUidFromCustomCode(customCode);
        //is reported ?
        int userReportState = contentMapper.getUserReportState(uid, cid);
        if (userReportState == 1) return false;//already
        // new report
        Timestamp date = new Timestamp(new Date().getTime());
        return contentMapper.addReportContent(uid, cid, reason, date) > 0;
    }

    // 内容发布 //
    // 内容发布 //
    // 内容发布 //


    /**
     * 发布内容
     */
    public boolean publishContent(String customCode, Content content) {
        //登录判断
        if (!redisHelper.hasCustomCode(customCode)) return false;
        //设置内容的非用户编辑属性
        content.setCid(UUIDGenerator.generate());
        content.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        content.setLikeAmount(0);
        content.setUid(redisHelper.getUidFromCustomCode(customCode));
        //保存
        return contentMapper.publishContent(content) > 0;
    }

    /**
     * 修改发布的内容
     */
    public boolean updateContent(Content content) {
        return contentMapper.updateContent(content);
    }

    /**
     * 指示开始上传文件，如果调用该方法后半小时仍然没有指示结束上传，那么资源将作废
     *
     * @param customCode 用户自定义登录记录
     * @param type       上传的资源类型，对应正在上传的内容的类型，可为"picture"/"video"
     * @return 当前上传文件操作的ID，在后续上传操作中需要用到
     */
    public String startUploadFile(String customCode, String type) {
        //check customCode
        if (!redisHelper.hasCustomCode(customCode)) return "";
        //create redis record
        String uploadId = UUIDGenerator.generate();
        try {
            Thread.sleep(10);//to get different uuid
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String sid = UUIDGenerator.generate() + uploadId.hashCode();
        redisHelper.setUploadId(uploadId, sid, type);
        // create database sid record
        resourceMapper.insertNewEmpty(sid, type);
        //
        return uploadId;
    }


    /**
     * 上传文件
     *
     * @param uploadId 在指示开启上传文件方法中获得的上传ID
     * @param file     要上传的文件
     * @return 成功返回true，失败（文件类型非法）时返回false
     */
    public boolean uploadFile(String uploadId, MultipartFile file) {
        //check upload id
        if (!redisHelper.hasUploadId(uploadId)) return false;

        //make destination path
        StringBuilder toStoreFilename = new StringBuilder();
        //check upload type
        String uploadType = redisHelper.getUploadType(uploadId);
        if (uploadType.equals("picture")) {
            //check extension
            Set<String> availableFiles = new HashSet<>(Arrays.asList("jpg", "png", "jpeg"));
            if (!availableFiles.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) return false;
            //set dir
            toStoreFilename.append("picture/");
        } else if (uploadType.equals("video")) {
            //check extension
            if (!"mp4".equals(FilenameUtils.getExtension(file.getOriginalFilename()))) return false;
            //set dir
            toStoreFilename.append("vid/");
        } else return false;
        //generate filename
        toStoreFilename.append(UUIDGenerator.generate()).append(FilenameUtils.getExtension(file.getOriginalFilename()));
        //make mapped url
        String toSavePath = staticMappingHelper.getStaticLocations() + toStoreFilename;

        //save file
        try {
            file.transferTo(new File(toSavePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //add url to database
        Resource resource = resourceMapper.getBySid(redisHelper.getUploadSid(uploadId));
        try {
            ObjectMapper om = new ObjectMapper();
            //get json array as list
            List<String> paths = om.readValue(resource.getPaths(), new TypeReference<List<String>>() {
            });
            //add new file name
            paths.add(toStoreFilename.toString());
            //covert back to json array
            resource.setPaths(om.writeValueAsString(paths));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //
        return true;
    }

    /**
     * 指示结束上传文件，将之前上传的文件合并为一个资源并获得其资源sid
     *
     * @param uploadId 上传ID
     * @return 资源ID，sid,需要填写到发布内容的sid属性内
     */
    public String finishUploadFile(String uploadId) {
        //check upload id
        if (!redisHelper.hasUploadId(uploadId)) return "";
        //get sid
        String sid = redisHelper.getUploadSid(uploadId);
        //remove the upload id
        redisHelper.removeUploadId(uploadId);
        //
        return sid;
    }

    // 内容评论 //
    // 内容评论 //
    // 内容评论 //

    public List<ReplyVO> getReply(String cid) {
        //获取顶层评论
        List<ReplyVO> topReply = replyMapper.getAllTopReplyOfContent(cid);
        //遍历楼中楼，填充楼中楼评论
        topReply.forEach(top -> {
            top.setUserAvatarPath(staticMappingHelper.doMapToDomain(top.getUserAvatarPath()));
            List<ReplyVO> subReply = replyMapper.getAllSubReply(top.getRid());
            subReply.forEach(sub -> {
                sub.setUserAvatarPath(staticMappingHelper.doMapToDomain(sub.getUserAvatarPath()));
                if (sub.getInner() == null || sub.getInner().isEmpty()) {
                    sub.setInnerGoalNickname(userMapper.getUserByUid(sub.getInnerGoalNickname()).getNickname());
                }
            });
            top.setInner(subReply);
        });
        return topReply;
    }

    public boolean addReply(String customCode, String goal_id, String goal_type, String content) {
        //check custom code
        if (!redisHelper.hasCustomCode(customCode)) return false;
        //check type
        if (!"inner".equals(goal_type) && !"top".equals(goal_type)) return false;
        //build reply
        Reply reply = new Reply();
        reply.setUid(redisHelper.getUidFromCustomCode(customCode));
        reply.setContent(content);
        reply.setGoal(goal_id);
        reply.setDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        reply.setLikeAmount(0);
        reply.setRid(UUIDGenerator.generate());
        //save
        replyMapper.addReply(reply);
        //
        return true;
    }


    public int getPageAmount(int queryId) {
        //计算逆序页，获得数据
        return queryId < PAGESIZE ? 1 : queryId / PAGESIZE;
    }

    //默认获取8个卡片
    private static final int PAGESIZE = 8;

    @Autowired
    ContentMapper contentMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CollectionMapper collectionMapper;

    @Autowired
    StaticMappingHelper staticMappingHelper;

    @Autowired
    private RedisHelper redisHelper;


}
