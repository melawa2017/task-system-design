package com.stc.digi.system_design.controllers;


import com.stc.digi.system_design.dtos.CreateItemDto;
import com.stc.digi.system_design.dtos.ResponseFile;
import com.stc.digi.system_design.entities.FileTable;
import com.stc.digi.system_design.services.PermissionService;
import com.stc.digi.system_design.util.IResponseCode;
import com.stc.digi.system_design.util.RC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/space")
    public ResponseEntity createSpace(@RequestBody CreateItemDto createItemDto) {

        try {
            IResponseCode iResponseCode = permissionService.saveSpace(createItemDto);
            if (iResponseCode.getRc().equals(RC.Success))
                return ResponseEntity.ok().body("ok");
            else
                return errorResponse(iResponseCode.getRc().name());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Error:- " + e.getMessage());

        }
    }

    @PostMapping("/folder")
    public ResponseEntity createFolder(@RequestBody CreateItemDto createItemDto) {

        try {
            IResponseCode iResponseCode = permissionService.saveFolder(createItemDto);
            if (iResponseCode.getRc().equals(RC.Success))
                return ResponseEntity.ok().body("ok");
            else
                return errorResponse(iResponseCode.getRc().name());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body("Error:- " + e.getMessage());

        }
    }

    @PostMapping(value = "/file", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createFile(@RequestPart("fileInfo") CreateItemDto createItemDto, @RequestPart("file") MultipartFile file) {
        try {
            IResponseCode iResponseCode = permissionService.saveFile(createItemDto, file);
            if (iResponseCode.getRc().equals(RC.Success)) {
                ResponseFile responseFile = (ResponseFile) iResponseCode.getObject();
                return ResponseEntity.ok().body(responseFile);
            } else
                return errorResponse(iResponseCode.getRc().name());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename() + "!");

        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity getFile(@PathVariable String id, @RequestParam String loginUser) {

        IResponseCode iResponseCode = permissionService.getFile(id, loginUser);
        if (iResponseCode.getRc().equals(RC.Success)) {
            FileTable fileTable = (FileTable) iResponseCode.getObject();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileTable.getItemTable().getName() + "\"")
                    .body(fileTable.getBinaryFile());
        } else
            return errorResponse(iResponseCode.getRc().name());

    }

    private ResponseEntity errorResponse(String rc) {
        if (rc.equals(RC.Permission.name())) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Permission denied");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("General Exception");
        }
    }
}
