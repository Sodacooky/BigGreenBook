package main.biggreenbook.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/*
将原本如img/aaa.jpg的路径名称
映射为 <配置文件中配置的url pattern>/img/aaa.jpg
 */
@Component
@PropertySource("classpath:application.yml")
public class FilePathMappingHelper {
    @Value("${spring.mvc.static-path-pattern}")
    String pattern;

    public String doMap(String src) {
        checkPatternOrEditIt();
        if (src.startsWith("/")) return pattern.substring(0, pattern.length() - 1) + src;
        else return pattern + src;
    }

    private void checkPatternOrEditIt() {
        if (pattern.startsWith("/")) pattern = pattern.substring(1);
        if (pattern.endsWith("*")) pattern = pattern.replaceAll("[*]", "");
    }
}
