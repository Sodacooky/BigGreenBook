package main.biggreenbook.entity.pojo;

import java.sql.Timestamp;

public class Reply {
    private String rid;
    private String type; //top,inner
    private String content;
    private String goal;
    private Integer likeAmount;
    private Timestamp date;
    private String uid;

    public Reply() {
    }

    public Reply(String rid, String type, String content, String goal, Integer likeAmount, Timestamp date, String uid) {
        this.rid = rid;
        this.type = type;
        this.content = content;
        this.goal = goal;
        this.likeAmount = likeAmount;
        this.date = date;
        this.uid = uid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
