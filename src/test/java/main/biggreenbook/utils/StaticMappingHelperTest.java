package main.biggreenbook.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StaticMappingHelperTest {

    @Autowired
    private StaticMappingHelper staticMappingHelper;

    @Test
    public void doMapToRootTestA() {
        String original = "/unreal/picture.jpg";
        System.out.println("original: " + original);
        System.out.println("after: " + staticMappingHelper.doMapToRoot(original));
    }

    @Test
    public void doMapToRootTestB() {
        String original = "unreal/picture2.jpg";
        System.out.println("original: " + original);
        System.out.println("after: " + staticMappingHelper.doMapToRoot(original));
    }

    @Test
    public void doMapToDomainTestA() {
        String original = "/unreal/picture3.jpg";
        System.out.println("original: " + original);
        System.out.println("after: " + staticMappingHelper.doMapToDomain(original));
    }

    @Test
    public void doMapToDomainTestB() {
        String original = "unreal/picture4.jpg";
        System.out.println("original: " + original);
        System.out.println("after: " + staticMappingHelper.doMapToDomain(original));
    }

}
