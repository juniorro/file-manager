package io.heat.service;

import io.heat.domain.MyFile;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@CommonsLog
@RunWith(SpringRunner.class)
public class OutBoundTests {

    private MyFile file = new MyFile();

    @Autowired
    private InBound in;

    @Autowired
    private OutBound out;

    @Before
    public void setup() throws Exception{
        file.setShortName("app.properties");
        file.setOwnerName("testOwner");
        Path path = Paths.get(ClassLoader.getSystemResource("").getPath()+"application.properties");
        file.setRaw(Base64.encodeBase64String(Files.readAllBytes(path)));
        in.storeFile(file);
    }

    @Test
    public void testUsersFiles() throws Exception{
        List<MyFile> outList = out.usersFiles("testOwner");
        outList.forEach(o -> log.info(o.toString()));
        Assert.assertEquals("app.properties",outList.get(0).getShortName());
    }

    @Test
    public void testAllFiles() throws Exception{
        List<List<MyFile>> outList = out.allFiles();
        outList.forEach(myFiles -> log.info(myFiles.size()));
        log.info(outList.size());
        Assert.assertEquals("app.properties",outList.get(0).get(0).getShortName());
    }

    @TestConfiguration
    static class TestConfigService {

        @Bean
        public InBound inBound(){ return new InBound(); }

        @Bean
        public OutBound outBound(){ return new OutBound(); }
    }
}
