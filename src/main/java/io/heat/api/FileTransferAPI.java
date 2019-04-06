package io.heat.api;

import io.heat.domain.MyFile;
import io.heat.domain.TransferReport;
import io.heat.service.InBound;
import io.heat.service.OutBound;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@RestController
@RequestMapping("/transfer")
@AllArgsConstructor
@CommonsLog
public class FileTransferAPI {

    private InBound in;

    private OutBound out;

    @PostMapping(value = "/file")
    public ResponseEntity<Resource> storeFiles(@RequestBody MyFile file) throws Exception{
        in.storeFile(file);
        return ResponseEntity.ok(new Resource(new TransferReport(1,0), linkTo(methodOn(FileTransferAPI.class).storeFiles(file)).withSelfRel()));
    }

    @PostMapping(value = "/files")
    public ResponseEntity<Resource> storeFiles(@RequestBody List<MyFile> files) throws Exception{
        TransferReport report = in.storeFiles(files);
        return ResponseEntity.ok(new Resource(report, linkTo(methodOn(FileTransferAPI.class).storeFiles(files)).withSelfRel()));
    }

    @GetMapping("/files")
    public ResponseEntity<List<?>> getAllFiles() throws Exception{
        List<List<MyFile>> resourceList = out.allFiles();
        return ResponseEntity.ok(resourceList);
    }

    @GetMapping("/files/{ownerName}")
    public ResponseEntity<Resources<Resource<MyFile>>> getOwnersFiles(@PathVariable String ownerName) throws Exception{
        List<Resource<MyFile>> resourceList = out.usersFiles(ownerName)
                .stream()
                .map(file -> {
                    Resource<MyFile> resource = null;
                    try {
                        resource = new Resource<>(file, linkTo(FileTransferAPI.class).slash("files/").slash(file.getOwnerName()).slash(file.getShortName()).withSelfRel());
                    } catch (Exception e) {
                    }
                    return resource;
            }).collect(Collectors.toList());

        return ResponseEntity.ok(
                new Resources<>(resourceList,
                       linkTo(methodOn(FileTransferAPI.class).getOwnersFiles(ownerName)).withSelfRel())
                );
    }
}
