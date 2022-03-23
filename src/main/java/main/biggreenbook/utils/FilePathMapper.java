package main.biggreenbook.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

//读取配置文件，便利生成目录路径
public class FilePathMapper {

    static {
        Properties properties = new Properties();
        try {
            properties.load(FilePathMapper.class.getClassLoader().getResourceAsStream("application.properties"));
            filePath = properties.getProperty("file.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String filePath = "res";

    public static String getPictureFilePath(String originalFilename) {
        // filePath/image/file.jpg
        return filePath + File.separator + "image" + File.separator + originalFilename;
    }

    public static String getVideoFilePath(String originalFilename) {
        // filePath/video/video.mp4
        return filePath + File.separator + "video" + File.separator + originalFilename;
    }

}
