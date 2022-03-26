package main.biggreenbook.pojo;


import java.sql.Date;

public class User {
    private String uid;
    private String nickname;
    private String description;
    private Integer gender;
    private Date birthday;
    private Integer state;
    private String avatarPath;

    public User() {
    }

    public User(String uid, String nickname, String description, Integer gender, Date birthday, Integer state, String avatarPath) {
        this.uid = uid;
        this.nickname = nickname;
        this.description = description;
        this.gender = gender;
        this.birthday = birthday;
        this.state = state;
        this.avatarPath = avatarPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", description='" + description + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", state=" + state +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
