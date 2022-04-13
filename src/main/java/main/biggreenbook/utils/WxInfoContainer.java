package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 存放与微信API相关的参数，多为敏感内容
 */
@Component
@PropertySource("classpath:/wx.yml")
public class WxInfoContainer {

    @Value("wx.appId")
    private String appId;

    @Value("wx.secret")
    private String secret;

    public WxInfoContainer() {
    }

    public WxInfoContainer(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
