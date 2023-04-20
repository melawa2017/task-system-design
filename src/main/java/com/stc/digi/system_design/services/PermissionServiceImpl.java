package com.stc.digi.system_design.services;

import com.stc.digi.system_design.dtos.CreateItemDto;
import com.stc.digi.system_design.dtos.ResponseFile;
import com.stc.digi.system_design.entities.FileTable;
import com.stc.digi.system_design.entities.ItemTable;
import com.stc.digi.system_design.entities.PermissionGroupTable;
import com.stc.digi.system_design.entities.PermissionTable;
import com.stc.digi.system_design.repositories.FileRepository;
import com.stc.digi.system_design.repositories.ItemRepository;
import com.stc.digi.system_design.repositories.PermissionGroupRepository;
import com.stc.digi.system_design.repositories.PermissionRepository;
import com.stc.digi.system_design.util.IResponseCode;
import com.stc.digi.system_design.util.ItemType;
import com.stc.digi.system_design.util.PermissionLevel;
import com.stc.digi.system_design.util.RC;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Boolean checkUserPermission(Long userId, Long ItemId) {
        // get user
        PermissionTable user = permissionRepository.findById(userId).get();
        if (user != null) {
            // get item
            ItemTable item = itemRepository.findById(ItemId).get();
            if (item != null) {
                // check Space / Folder / File permission
                if (item.getType().equals(ItemType.SPACE.name())
                        && item.getPermissionGroupTable().getGroup_name().equals(user.getPermissionGroupTable().getGroup_name())
                        && user.getPermissionLevel().equals(PermissionLevel.EDIT.name())) {
                    return true;
                } else if (item.getType().equals(ItemType.FOLDER.name())
                        && item.getPermissionGroupTable().getGroup_name().equals(user.getPermissionGroupTable().getGroup_name())
                        && user.getPermissionLevel().equals(PermissionLevel.EDIT.name())) {
                    return true;
                } else if (item.getType().equals(ItemType.FILE.name())
                        && item.getPermissionGroupTable().getGroup_name().equals(user.getPermissionGroupTable().getGroup_name())
                        && user.getPermissionLevel().equals(PermissionLevel.EDIT.name())) {
                    return true;
                }
            }
        }
        return false;
    }

    @TransactionScoped // ---------NOTE ---------
    @Override
    public IResponseCode saveSpace(CreateItemDto createItemDto) {

        try {
            // create Group
            PermissionGroupTable savedGroup = permissionGroupRepository.save(new PermissionGroupTable()
                    .builder()
                    .group_name(createItemDto.getCreateGroupDto().getGroupName())
                    .build());
            if (savedGroup != null) {
                // save users under Group
                for (int i = 0; i < createItemDto.getCreateGroupDto().getCreatePermissionDtoList().size(); i++) {
                    permissionRepository.save(new PermissionTable()
                            .builder()
                            .userEmail(createItemDto.getCreateGroupDto().getCreatePermissionDtoList().get(i).getUserEmail())
                            .permissionLevel(createItemDto.getCreateGroupDto().getCreatePermissionDtoList().get(i).getPermissionLevel())
                            .permissionGroupTable(savedGroup)
                            .build());
                }

                // save item
                itemRepository.save(new ItemTable()
                        .builder()
                        .name(createItemDto.getName())
                        .type(createItemDto.getItemType())
                        .permissionGroupTable(savedGroup)
                        .build());

                return new IResponseCode(RC.Success, null);

            }

            return new IResponseCode(RC.Exception, null);
        } catch (Exception e) {
            return new IResponseCode(RC.Exception, null);
        }
    }

    @Override
    public IResponseCode saveFolder(CreateItemDto createItemDto) {
        try {
            // get login user permission
            PermissionTable permissionTable = permissionRepository.findByUserEmail(createItemDto.getLoginUser()).get();
            if (permissionTable != null) {
                // check user permission for current login user & parent item #"stc-assessments"
                if (checkUserPermission(permissionTable.getId(), createItemDto.getItemParentId())) {
                    // save Folder / File item
                    ItemTable savedItem = itemRepository.save(new ItemTable()
                            .builder()
                            .name(createItemDto.getName())
                            .type(createItemDto.getItemType())
                            .parentItem(itemRepository.findById(createItemDto.getItemParentId()).get())
                            .permissionGroupTable(permissionTable.getPermissionGroupTable())
                            .build());

                    return new IResponseCode(RC.Success, null);

                } else {
                    return new IResponseCode(RC.Permission, null);
                }
            }
            return new IResponseCode(RC.Exception, null);
        } catch (Exception e) {
            return new IResponseCode(RC.Exception, null);
        }
    }

    @Override
    public IResponseCode saveFile(CreateItemDto createItemDto, MultipartFile file) {
        try {
            // get login user permission
            PermissionTable permissionTable = permissionRepository.findByUserEmail(createItemDto.getLoginUser()).get();
            if (permissionTable != null) {
                // check user permission for current login user & parent item #"stc-assessments"
                if (checkUserPermission(permissionTable.getId(), createItemDto.getItemParentId())) {
                    // save Folder / File item
                    ItemTable savedItem = itemRepository.save(new ItemTable()
                            .builder()
                            .name(createItemDto.getName())
                            .type(createItemDto.getItemType())
                            .parentItem(itemRepository.findById(createItemDto.getItemParentId()).get())
                            .permissionGroupTable(permissionTable.getPermissionGroupTable())
                            .build());

                    // if file present save it into database
                    if (savedItem != null && file != null) {
                        FileTable fileTable = fileRepository.save(new FileTable()
                                .builder()
                                .binaryFile(file.getBytes())
                                .ItemTable(savedItem)
                                .build());


                        String fileDownloadUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/item/files/")
                                .path(fileTable.getId() + "")
                                .toUriString();

                        return new IResponseCode(RC.Success, new ResponseFile(
                                file.getName(),
                                fileDownloadUri,
                                file.getContentType(),
                                file.getSize()));
                    }
                } else {
                    return new IResponseCode(RC.Permission, null);
                }
            }
            return new IResponseCode(RC.Exception, null);
        } catch (Exception e) {
            return new IResponseCode(RC.Exception, null);
        }

    }

    @Override
    public IResponseCode getFile(String id, String loginUser) {
        try {
            // get login user permission
            PermissionTable permissionTable = permissionRepository.findByUserEmail(loginUser).get();
            FileTable fileTable = fileRepository.findById(Long.parseLong(id)).get();
            if (permissionTable != null && fileTable != null) {
                // check user permission for current login user & parent item #"stc-assessments"
                if (checkUserPermission(permissionTable.getId(), fileTable.getItemTable().getId())) {
                    return new IResponseCode(RC.Success, fileTable);
                } else {
                    return new IResponseCode(RC.Permission, null);
                }
            }
            return new IResponseCode(RC.Exception, null);
        } catch (Exception e) {
            return new IResponseCode(RC.Exception, null);
        }
    }
}
