package com.stc.digi.system_design.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stc.digi.system_design.dtos.CreateItemDto;
import com.stc.digi.system_design.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/space")
    public ResponseEntity createSpace(@RequestBody CreateItemDto createItemDto) {

        if (permissionService.saveSpace(createItemDto))
            return ResponseEntity.ok().body("ok");
        return ResponseEntity.ok().body("Error");
    }

    @PostMapping("/folder")
    public ResponseEntity createFolder(@RequestBody CreateItemDto createItemDto) {

        if (permissionService.saveFolderOrFile(createItemDto))
            return ResponseEntity.ok().body("ok");
        return ResponseEntity.ok().body("Error user have not permission");
    }

    @PostMapping("/file")
    public ResponseEntity createFile(@RequestBody CreateItemDto createItemDto) {
        if (permissionService.saveFolderOrFile(createItemDto))
            return ResponseEntity.ok().body("ok");
        return ResponseEntity.ok().body("Error user have not permission");
    }
}
