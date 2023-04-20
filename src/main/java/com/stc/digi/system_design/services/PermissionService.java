package com.stc.digi.system_design.services;

import com.stc.digi.system_design.dtos.CreateItemDto;
import com.stc.digi.system_design.dtos.ResponseFile;
import com.stc.digi.system_design.entities.FileTable;
import com.stc.digi.system_design.util.IResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PermissionService {

    Boolean checkUserPermission(Long userId, Long ItemId);

    IResponseCode saveSpace(CreateItemDto createItemDto);

    IResponseCode saveFolder(CreateItemDto createItemDto);

    IResponseCode saveFile(CreateItemDto createItemDto, MultipartFile file) throws IOException;

    IResponseCode getFile(String id, String loginUser);
}
