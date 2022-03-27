package main.biggreenbook.entity.vo;

/*
瀑布流、收藏夹等地方使用到的预览卡片所需要的数据
 */
public class PreviewCard {
    //0: picture, 1: video
    Integer resourceType;
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

    public PreviewCard(Integer resourceType, String resourcePath, String contentTitle, String userAvatarPath, String userNickname, Integer contentLikeAmount) {
        this.resourceType = resourceType;
        this.resourcePath = resourcePath;
        this.contentTitle = contentTitle;
        this.userAvatarPath = userAvatarPath;
        this.userNickname = userNickname;
        this.contentLikeAmount = contentLikeAmount;
    }

    public PreviewCard() {
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
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

    @Override
    public String toString() {
        return "PreviewCard{" +
                "resourceType=" + resourceType +
                ", resourcePath='" + resourcePath + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", userAvatarPath='" + userAvatarPath + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", contentLikeAmount=" + contentLikeAmount +
                '}';
    }
}
