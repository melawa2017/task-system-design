package com.stc.digi.system_design.services;

import com.stc.digi.system_design.dtos.CreateItemDto;
import org.springframework.stereotype.Service;


public interface PermissionService {

    Boolean checkUserPermission(Long userId , Long ItemId);

    Boolean saveSpace(CreateItemDto createItemDto);

    Boolean saveFolderOrFile(CreateItemDto createItemDto);
}
