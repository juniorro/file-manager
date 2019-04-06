package io.heat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.heat.domain.MyFile;
import io.heat.domain.TransferReport;
import io.heat.service.InBound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FileTransferAPI.class)
public class FileTransferAPITests {

    @MockBean
    InBound in;

    @Autowired
    public MockMvc transferResource;

    MyFile iFile;

    @Before
    public void setup(){
        iFile = new MyFile();
        iFile.setShortName("file_1");
        iFile.setRaw("PHN2ZyBmaWxsPSIjMDAwMDAwIiBoZWlnaHQ9IjI0IiB2aWV3Qm94PSIwIDAgMjQgMjQiIHdpZHRoPSIyNCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICAgIDxwYXRoIGQ9Ik0xNy44MSA0LjQ3Yy0uMDggMC0uMTYtLjAyLS4yMy0uMDZDMTUuNjYgMy40MiAxNCAzIDEyLjAxIDNjLTEuOTggMC0zLjg2LjQ3LTUuNTcgMS40MS0uMjQuMTMtLjU0LjA0LS42OC0uMi0uMTMtLjI0LS4wNC0uNTUuMi0uNjhDNy44MiAyLjUyIDkuODYgMiAxMi4wMSAyYzIuMTMgMCAzLjk5LjQ3IDYuMDMgMS41Mi4yNS4xMy4zNC40My4yMS42Ny0uMDkuMTgtLjI2LjI4LS40NC4yOHpNMy41IDkuNzJjLS4xIDAtLjItLjAzLS4yOS0uMDktLjIzLS4xNi0uMjgtLjQ3LS4xMi0uNy45OS0xLjQgMi4yNS0yLjUgMy43NS0zLjI3QzkuOTggNC4wNCAxNCA0LjAzIDE3LjE1IDUuNjVjMS41Ljc3IDIuNzYgMS44NiAzLjc1IDMuMjUuMTYuMjIuMTEuNTQtLjEyLjctLjIzLjE2LS41NC4xMS0uNy0uMTItLjktMS4yNi0yLjA0LTIuMjUtMy4zOS0yLjk0LTIuODctMS40Ny02LjU0LTEuNDctOS40LjAxLTEuMzYuNy0yLjUgMS43LTMuNCAyLjk2LS4wOC4xNC0uMjMuMjEtLjM5LjIxem02LjI1IDEyLjA3Yy0uMTMgMC0uMjYtLjA1LS4zNS0uMTUtLjg3LS44Ny0xLjM0LTEuNDMtMi4wMS0yLjY0LS42OS0xLjIzLTEuMDUtMi43My0xLjA1LTQuMzQgMC0yLjk3IDIuNTQtNS4zOSA1LjY2LTUuMzlzNS42NiAyLjQyIDUuNjYgNS4zOWMwIC4yOC0uMjIuNS0uNS41cy0uNS0uMjItLjUtLjVjMC0yLjQyLTIuMDktNC4zOS00LjY2LTQuMzktMi41NyAwLTQuNjYgMS45Ny00LjY2IDQuMzkgMCAxLjQ0LjMyIDIuNzcuOTMgMy44NS42NCAxLjE1IDEuMDggMS42NCAxLjg1IDIuNDIuMTkuMi4xOS41MSAwIC43MS0uMTEuMS0uMjQuMTUtLjM3LjE1em03LjE3LTEuODVjLTEuMTkgMC0yLjI0LS4zLTMuMS0uODktMS40OS0xLjAxLTIuMzgtMi42NS0yLjM4LTQuMzkgMC0uMjguMjItLjUuNS0uNXMuNS4yMi41LjVjMCAxLjQxLjcyIDIuNzQgMS45NCAzLjU2LjcxLjQ4IDEuNTQuNzEgMi41NC43MS4yNCAwIC42NC0uMDMgMS4wNC0uMS4yNy0uMDUuNTMuMTMuNTguNDEuMDUuMjctLjEzLjUzLS40MS41OC0uNTcuMTEtMS4wNy4xMi0xLjIxLjEyek0xNC45MSAyMmMtLjA0IDAtLjA5LS4wMS0uMTMtLjAyLTEuNTktLjQ0LTIuNjMtMS4wMy0zLjcyLTIuMS0xLjQtMS4zOS0yLjE3LTMuMjQtMi4xNy01LjIyIDAtMS42MiAxLjM4LTIuOTQgMy4wOC0yLjk0IDEuNyAwIDMuMDggMS4zMiAzLjA4IDIuOTQgMCAxLjA3LjkzIDEuOTQgMi4wOCAxLjk0czIuMDgtLjg3IDIuMDgtMS45NGMwLTMuNzctMy4yNS02LjgzLTcuMjUtNi44My0yLjg0IDAtNS40NCAxLjU4LTYuNjEgNC4wMy0uMzkuODEtLjU5IDEuNzYtLjU5IDIuOCAwIC43OC4wNyAyLjAxLjY3IDMuNjEuMS4yNi0uMDMuNTUtLjI5LjY0LS4yNi4xLS41NS0uMDQtLjY0LS4yOS0uNDktMS4zMS0uNzMtMi42MS0uNzMtMy45NiAwLTEuMi4yMy0yLjI5LjY4LTMuMjQgMS4zMy0yLjc5IDQuMjgtNC42IDcuNTEtNC42IDQuNTUgMCA4LjI1IDMuNTEgOC4yNSA3LjgzIDAgMS42Mi0xLjM4IDIuOTQtMy4wOCAyLjk0cy0zLjA4LTEuMzItMy4wOC0yLjk0YzAtMS4wNy0uOTMtMS45NC0yLjA4LTEuOTRzLTIuMDguODctMi4wOCAxLjk0YzAgMS43MS42NiAzLjMxIDEuODcgNC41MS45NS45NCAxLjg2IDEuNDYgMy4yNyAxLjg1LjI3LjA3LjQyLjM1LjM1LjYxLS4wNS4yMy0uMjYuMzgtLjQ3LjM4eiIvPgogICAgPHBhdGggZD0iTTAgMGgyNHYyNEgweiIgZmlsbD0ibm9uZSIvPgo8L3N2Zz4=");
        iFile.setOwnerName("eraphael");
    }

    @Test
    public void testStoreFiles() throws Exception{
        BDDMockito.given(in.storeFiles(new ArrayList<>())).willReturn(new TransferReport());

        transferResource.perform(
                MockMvcRequestBuilders.post("/transfer/file")
                        .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(iFile)))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
