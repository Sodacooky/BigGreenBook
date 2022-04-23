package main.biggreenbook.entity.vo;

import java.util.List;

public class ContentInfo {
    //内容cid、标题、正文、日期、点赞数
    private String cid;
    private String title;
    private String text;
    private String date;
    private String likeAmount;

    private String tag; //json array

    private List<String> tags; //part tag

    //资源sid、路径
    private String sid;
    private String path;
    private List<String> paths;

    //发布者uid、昵称、头像、关注状态
    private String uid;
    private String userNickname;
    private String userAvatarPath;

    //是否已关注
    private int status;
    //是否已点赞
    private int like;
    //是否已收藏
    private int collection;

    public ContentInfo() {
    }

    public ContentInfo(String cid, String title, String text, String date, String likeAmount, String tag, List<String> tags, String sid, String path, List<String> paths, String uid, String userNickname, String userAvatarPath, int status, int like, int collection) {
        this.cid = cid;
        this.title = title;
        this.text = text;
        this.date = date;
        this.likeAmount = likeAmount;
        this.tag = tag;
        this.tags = tags;
        this.sid = sid;
        this.path = path;
        this.paths = paths;
        this.uid = uid;
        this.userNickname = userNickname;
        this.userAvatarPath = userAvatarPath;
        this.status = status;
        this.like = like;
        this.collection = collection;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(String likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserAvatarPath() {
        return userAvatarPath;
    }

    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ContentInfo{" +
                "cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", likeAmount='" + likeAmount + '\'' +
                ", tag='" + tag + '\'' +
                ", tags=" + tags +
                ", sid='" + sid + '\'' +
                ", path='" + path + '\'' +
                ", paths=" + paths +
                ", uid='" + uid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvatarPath='" + userAvatarPath + '\'' +
                ", status=" + status +
                ", like=" + like +
                ", collection=" + collection +
                '}';
    }
}

