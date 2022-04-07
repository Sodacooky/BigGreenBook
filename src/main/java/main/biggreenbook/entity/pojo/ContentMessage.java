package main.biggreenbook.entity.pojo;

import main.biggreenbook.entity.pojo.Content;


import java.util.Date;
import java.util.List;

public class ContentMessage {
    private String cid;
    private String title;
    private String author;
    private Date date;
    private Integer type; //0: picture, 1: video
    private Integer likeAmount;
    private String uid;
    private String path;

    public ContentMessage() {
    }

    public ContentMessage(String cid, String title, String author, Date date, Integer type, Integer likeAmount, String uid, String path) {
        this.cid = cid;
        this.title = title;
        this.author = author;
        this.date = date;
        this.type = type;
        this.likeAmount = likeAmount;
        this.uid = uid;
        this.path = path;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(Integer likeAmount) {
        this.likeAmount = likeAmount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPath() {
        return path.replace("\"", "");
    }

    public void setPath(String path) {
        this.path = path;
    }
}
