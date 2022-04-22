package main.biggreenbook.entity.vo;

import java.sql.Timestamp;
import java.util.List;

//每个ReplyVO对应一个顶层回复
//最多内嵌一层ReplyVO
public class ReplyVO {

    //评论核心数据
    private String rid;
    private String content;

    //用户信息区
    private String userUid;
    private String userNickname;
    private String userAvatarPath;

    //评论信息
    private Timestamp date;
    private Integer likeAmount;

    //楼中楼
    List<ReplyVO> inner;
    //楼中楼需要用到回复对象昵称
    private String innerGoalNickname;

    public ReplyVO() {
    }

    public ReplyVO(String rid, String content, String userUid, String userNickname, String userAvatarPath, Timestamp date, Integer likeAmount, List<ReplyVO> inner, String innerGoalNickname) {
        this.rid = rid;
        this.content = content;
        this.userUid = userUid;
        this.userNickname = userNickname;
        this.userAvatarPath = userAvatarPath;
        this.date = date;
        this.likeAmount = likeAmount;
        this.inner = inner;
        this.innerGoalNickname = innerGoalNickname;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public List<ReplyVO> getInner() {
        return inner;
    }

    public void setInner(List<ReplyVO> inner) {
        this.inner = inner;
    }

    public String getInnerGoalNickname() {
        return innerGoalNickname;
    }

    public void setInnerGoalNickname(String innerGoalNickname) {
        this.innerGoalNickname = innerGoalNickname;
    }

    @Override
    public String toString() {
        return "ReplyVO{" +
                "rid='" + rid + '\'' +
                ", content='" + content + '\'' +
                ", userUid='" + userUid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvatarPath='" + userAvatarPath + '\'' +
                ", date=" + date +
                ", likeAmount=" + likeAmount +
                ", inner=" + inner +
                ", innerGoalNickname='" + innerGoalNickname + '\'' +
                '}';
    }
}
