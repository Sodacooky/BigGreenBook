package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:application.yml")
public class UrlPathMapper {
    @Value("${spring.mvc.static-path-pattern}")
    String pattern;

    @Value("${domain}")
    String domain;

    /*
    将原本如img/aaa.jpg的路径名称
    映射为 <配置文件中配置的url pattern>/img/aaa.jpg
    如res/img/aaa.jpg
    */
    public String doMapToRoot(String src) {
        checkPatternOrEditIt();
        if (src.startsWith("/")) return pattern.substring(0, pattern.length() - 1) + src;
        else return pattern + src;
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
        if (pattern.startsWith("/")) pattern = pattern.substring(1);
        if (pattern.endsWith("*")) pattern = pattern.replaceAll("[*]", "");
    }

    private void checkDomainOrEditIt() {
        if (domain == null) {
            domain = "http://localhost:8080/";
        } else if (!domain.endsWith("/")) {
            domain = domain + "/";
        }
    }
}
