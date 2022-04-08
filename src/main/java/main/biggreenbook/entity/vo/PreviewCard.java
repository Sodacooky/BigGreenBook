package main.biggreenbook.entity.vo;

/*
瀑布流、收藏夹等地方使用到的预览卡片所需要的数据
 */
public class PreviewCard {
    //content's cid
    String contentCid;
    //picture or video
    String resourceType;
    //path to resource, url
    String resourcePath;
    //the title of the content
    String contentTitle;
    //path to user avatar resource, url
    String userAvatarPath;
    //the name of user
    String userNickname;
    //like amount
    Integer contentLikeAmount;

    public PreviewCard(String contentCid, String resourceType, String resourcePath, String contentTitle, String userAvatarPath, String userNickname, Integer contentLikeAmount) {
        this.contentCid = contentCid;
        this.resourceType = resourceType;
        this.resourcePath = resourcePath;
        this.contentTitle = contentTitle;
        this.userAvatarPath = userAvatarPath;
        this.userNickname = userNickname;
        this.contentLikeAmount = contentLikeAmount;
    }

    public PreviewCard() {
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getUserAvatarPath() {
        return userAvatarPath;
    }

    public void setUserAvatarPath(String userAvatarPath) {
        this.userAvatarPath = userAvatarPath;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer getContentLikeAmount() {
        return contentLikeAmount;
    }

    public void setContentLikeAmount(Integer contentLikeAmount) {
        this.contentLikeAmount = contentLikeAmount;
    }

    public String getContentCid() {
        return contentCid;
    }

    public void setContentCid(String contentCid) {
        this.contentCid = contentCid;
    }

    @Override
    public String toString() {
        return "PreviewCard{" +
                "contentCid='" + contentCid + '\'' +
                ", resourceType=" + resourceType +
                ", resourcePath='" + resourcePath + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", userAvatarPath='" + userAvatarPath + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", contentLikeAmount=" + contentLikeAmount +
                '}';
    }
}
