package main.biggreenbook.utils;

import java.util.UUID;

public class UUIDGenerator {

    //生成没有横杠的uuid
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
