package main.biggreenbook.entity.vo;

public class UserCard {
    //用户uid
    private String userUid;
    //用户昵称
    private String userNickname;
    //用户头像
    private String userAvatarPath;
    //发布笔记数量
    private Integer contentAmount;
    //粉丝数量
    private Integer fansAmount;
    //该用户是否已关注
    private Integer status;

    public UserCard() {
    }

    public UserCard(String userUid, String userNickname, String userAvatarPath, Integer contentAmount, Integer fansAmount, Integer status) {
        this.userUid = userUid;
        this.userNickname = userNickname;
        this.userAvatarPath = userAvatarPath;
        this.contentAmount = contentAmount;
        this.fansAmount = fansAmount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "userUid='" + userUid + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvatarPath='" + userAvatarPath + '\'' +
                ", contentAmount=" + contentAmount +
                ", fansAmount=" + fansAmount +
                ", status=" + status +
                '}';
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

    public Integer getContentAmount() {
        return contentAmount;
    }

    public void setContentAmount(Integer contentAmount) {
        this.contentAmount = contentAmount;
    }

    public Integer getFansAmount() {
        return fansAmount;
    }

    public void setFansAmount(Integer fansAmount) {
        this.fansAmount = fansAmount;
    }

    public Integer getIsStatus() {
        return status;
    }

    public void setIsStatus(Integer status) {
        this.status = status;
    }
}
