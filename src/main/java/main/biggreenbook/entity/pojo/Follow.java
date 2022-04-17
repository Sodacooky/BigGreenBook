package main.biggreenbook.entity.pojo;

import java.sql.Timestamp;

//一个关注关系
public class Follow {
    private String uid; //被关注者
    private String follower; //被谁关注
    private Timestamp date; //关注时间

    public Follow() {
    }

    public Follow(String uid, String follower, Timestamp date) {
        this.uid = uid;
        this.follower = follower;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
