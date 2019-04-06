package io.heat.service;

import io.heat.domain.MyFile;
import io.heat.domain.TransferReport;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@CommonsLog
@Service
public class InBound {

    public void storeFile(MyFile file) throws Exception{
        log.info("storing---");
        Path dir = Paths.get(Common.getFileStorage() + file.getOwnerName());

        if(!Files.exists(dir)){
            Files.createDirectory(dir);
        }

        Path p = Files.write(Paths.get(dir.toString()+Common.getFileSeparator()+file.getShortName()), Base64.decodeBase64(file.getRaw()));

    }

    public TransferReport storeFiles(List<MyFile> files) throws Exception{
        TransferReport report = new TransferReport();
        for (MyFile file: files){
            try{
                storeFile(file);
                report.setStoredFile(report.getStoredFile()+1);
            } catch (IOException ioe){
                report.setStoredFile(report.getFailedFile()+1);
            }
        }
        return report;
    }
}
