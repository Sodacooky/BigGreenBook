package main.biggreenbook.utils;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * 存放与微信API相关的参数，多为敏感内容
 */
@Component
public class WxInfoContainer {

    private String appId;
    private String secret;

    public WxInfoContainer() {
        //Springboot无法从多个配置文件中注入，故手动读取
        InputStream wxInfoStream = this.getClass().getClassLoader().getResourceAsStream("wxInfo.yml");
        Map<String, String> values = new Yaml().load(wxInfoStream);
        appId = values.get("appId");
        secret = values.get("secret");
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
