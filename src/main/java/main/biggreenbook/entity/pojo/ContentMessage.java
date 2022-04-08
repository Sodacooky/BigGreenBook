package main.biggreenbook.entity.pojo;

import java.util.Date;
import java.util.List;

public class ContentMessage {
    private String cid;
    private String title;
    private String text;
    private String author;
    private Date date;
    private Integer type; //0: picture, 1: video
    private Integer likeAmount;
    private String uid;
    private String path;
    private List<String> paths;

    public ContentMessage() {
    }

    public ContentMessage(String cid, String title, String text, String author, Date date, Integer type, Integer likeAmount, String uid, String path, List<String> paths) {
        this.cid = cid;
        this.title = title;
        this.text = text;
        this.author = author;
        this.date = date;
        this.type = type;
        this.likeAmount = likeAmount;
        this.uid = uid;
        this.path = path;
        this.paths = paths;
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

    @Override
    public String toString() {
        return "ContentMessage{" +
                "cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", likeAmount=" + likeAmount +
                ", uid='" + uid + '\'' +
                ", path='" + path + '\'' +
                ", paths=" + paths +
                '}';
    }
}
