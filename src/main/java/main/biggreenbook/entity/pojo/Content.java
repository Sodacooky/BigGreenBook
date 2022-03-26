package main.biggreenbook.entity.pojo;


import java.sql.Timestamp;

public class Content {
    private String cid;
    private String title;
    private String mainText;
    private Timestamp date;
    private Integer type;
    private Integer likeAmount;
    private String uid;
    private String sid;

    public Content() {
    }

    public Content(String cid, String title, String mainText, Timestamp date, Integer type, Integer likeAmount, String uid, String sid) {
        this.cid = cid;
        this.title = title;
        this.mainText = mainText;
        this.date = date;
        this.type = type;
        this.likeAmount = likeAmount;
        this.uid = uid;
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "Content{" +
                "cid='" + cid + '\'' +
                ", title='" + title + '\'' +
                ", mainText='" + mainText + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", likeAmount=" + likeAmount +
                ", uid='" + uid + '\'' +
                ", sid='" + sid + '\'' +
                '}';
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

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
