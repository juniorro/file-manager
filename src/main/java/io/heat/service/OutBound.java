package io.heat.service;

import io.heat.domain.MyFile;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@CommonsLog
@Service
public class OutBound {

    public List<MyFile> usersFiles(String ownerName) throws Exception {

        return
            Files.list(Paths.get(Common.getFileStorage()+Common.getFileSeparator()+ownerName))
                .map(path -> {
                    MyFile file = new MyFile();
                    try{
                        file.setRaw(Base64.encodeBase64String(Files.readAllBytes(path)));
                        file.setShortName(path.getFileName().toString());
                        file.setOwnerName(ownerName);
                    } catch (IOException e) {
                        // TODO
                    }
                    return file;
                })
                .collect(Collectors.toList());

    }

    public List<List<MyFile>> allFiles() throws Exception{
        return
            Files.list(Paths.get(Common.getFileStorage()+Common.getFileSeparator()))
                .map(path -> {
                    List<MyFile> myFileList = new ArrayList<>();
                    try{
                        if (Files.isDirectory(path)) {
                            myFileList = usersFiles(path.getFileName().toString());
                        }
                    } catch (Exception e) {
                        // TODO
                    }
                    return myFileList;
                }).collect(Collectors.toList());
    }
}
