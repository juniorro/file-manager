package io.heat.service;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
public class Common {

    public static String getFileStorage() throws Exception{
        String path = ClassLoader.getSystemResource("").getPath()
                +"static"
                +getFileSeparator();
        if (!Files.exists(Paths.get(path), LinkOption.NOFOLLOW_LINKS)){
            Files.createDirectory(Paths.get(path));
        }
        return path;
    }

    public static String getFileSeparator(){
        return System.getProperty("file.separator");
    }
}
