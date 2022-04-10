package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/application.yml")
public class StaticMappingHelper {

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPattern;

    @Value("${spring.web.resources.static-locations}")
    private String staticLocations;

    @Value("${my-env.domain}")
    private String domain;

    public StaticMappingHelper(String staticPattern, String staticLocations, String domainName) {
        this.staticPattern = staticPattern;
        this.staticLocations = staticLocations;
        this.domain = domainName;
    }

    public StaticMappingHelper() {
    }

    public String getStaticPattern() {
        return staticPattern;
    }

    public void setStaticPattern(String staticPattern) {
        this.staticPattern = staticPattern;
    }

    public String getStaticLocations() {
        return staticLocations;
    }

    public void setStaticLocations(String staticLocations) {
        this.staticLocations = staticLocations;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    /*
     * 将原本如img/aaa.jpg的路径名称
     * 映射为 <配置文件中配置的url pattern>/img/aaa.jpg
     * 如res/img/aaa.jpg
     */
    public String doMapToRoot(String src) {
        checkPatternOrEditIt();
        if (src.startsWith("/")) return staticPattern.substring(0, staticPattern.length() - 1) + src;
        else return staticPattern + src;
    }

    /*
    将原本如img/aaa.jpg的路径名称
    映射为 <配置文件中配置的domain>+<配置文件中配置的url pattern>/img/aaa.jpg
    如http://localhost:8080/res/img/aaa.jpg
    */
    public String doMapToDomain(String src) {
        checkDomainOrEditIt();
        return domain + doMapToRoot(src);
    }

    private void checkPatternOrEditIt() {
        if (staticPattern.startsWith("/")) staticPattern = staticPattern.substring(1);
        if (staticPattern.endsWith("*")) staticPattern = staticPattern.replaceAll("[*]", "");
    }

    private void checkDomainOrEditIt() {
        if (domain == null) {
            domain = "http://localhost:8080/";
        } else if (!domain.endsWith("/")) {
            domain = domain + "/";
        }
    }
}
