package main.biggreenbook.entity.pojo;

//用户的隐私设置
public class UserPrivacy {
    private String uid;
    private Integer publicCollection; //是否公开收藏夹
    private Integer publicLiked; //是否公开赞过

    public UserPrivacy() {
    }

    public UserPrivacy(String uid, Integer publicCollection, Integer publicLiked) {
        this.uid = uid;
        this.publicCollection = publicCollection;
        this.publicLiked = publicLiked;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPublicCollection() {
        return publicCollection;
    }

    public void setPublicCollection(Integer publicCollection) {
        this.publicCollection = publicCollection;
    }

    public Integer getPublicLiked() {
        return publicLiked;
    }

    public void setPublicLiked(Integer publicLiked) {
        this.publicLiked = publicLiked;
    }
}
