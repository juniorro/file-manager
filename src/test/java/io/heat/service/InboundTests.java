package io.heat.service;

import static org.junit.Assert.*;

import io.heat.domain.MyFile;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@CommonsLog
@RunWith(SpringRunner.class)
public class InboundTests {

    private MyFile file = new MyFile();

    @Autowired
    private InBound in;

    @Before
    public void setup() throws Exception{
        file.setShortName("app.properties");
        file.setOwnerName("testOwner");
        Path path = Paths.get(ClassLoader.getSystemResource("").getPath()+"application.properties");
        file.setRaw(Base64.encodeBase64String(Files.readAllBytes(path)));
    }

    @Test
    public void testStoreFile() throws Exception{
        in.storeFile(file);
        Path createdPath = Paths.get(Common.getFileStorage() + Common.getFileSeparator() + file.getOwnerName() + Common.getFileSeparator() + "app.properties");
        assertTrue(Files.exists(createdPath, LinkOption.NOFOLLOW_LINKS));
    }

    @TestConfiguration
    static class TestConfigService{

        @Bean
        public InBound inBound(){
            return new InBound();
        }
    }
}
