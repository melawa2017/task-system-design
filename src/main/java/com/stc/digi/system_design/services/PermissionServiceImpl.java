package com.stc.digi.system_design.services;

import com.stc.digi.system_design.dtos.CreateItemDto;
import com.stc.digi.system_design.entities.ItemTable;
import com.stc.digi.system_design.entities.PermissionGroupTable;
import com.stc.digi.system_design.entities.PermissionTable;
import com.stc.digi.system_design.repositories.ItemRepository;
import com.stc.digi.system_design.repositories.PermissionGroupRepository;
import com.stc.digi.system_design.repositories.PermissionRepository;
import com.stc.digi.system_design.util.ItemType;
import com.stc.digi.system_design.util.PermissionLevel;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Override
    public Boolean checkUserPermission(Long userId, Long ItemId) {
        // get user
        PermissionTable user = permissionRepository.findById(userId).get();
        if (user != null) {
            // get item
            ItemTable item = itemRepository.findById(ItemId).get();
            if (item != null) {
                // check Space / Folder permission
                if (item.getType().equals(ItemType.SPACE.name())
                        && item.getPermissionGroupTable().getGroup_name().equals(user.getPermissionGroupTable().getGroup_name())
                        && user.getPermissionLevel().equals(PermissionLevel.EDIT.name())) {
                    return true;
                } else if (item.getType().equals(ItemType.FOLDER.name())
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
    public Boolean saveSpace(CreateItemDto createItemDto) {

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

            return true;

        }

        return false;
    }

    @Override
    public Boolean saveFolderOrFile(CreateItemDto createItemDto) {
        // get login user permission
        PermissionTable permissionTable = permissionRepository.findByUserEmail(createItemDto.getLoginUser()).get();
        if (permissionTable != null) {
            // check user permission for current login user & parent item #"stc-assessments"
            if (checkUserPermission(permissionTable.getId(), createItemDto.getItemParentId())) {
                // save Folder item
                itemRepository.save(new ItemTable()
                        .builder()
                        .name(createItemDto.getName())
                        .type(createItemDto.getItemType())
                        .parentItem(itemRepository.findById(createItemDto.getItemParentId()).get())
                        .permissionGroupTable(permissionTable.getPermissionGroupTable())
                        .build());
                return true;

            } else {
                return false;
            }
        }
        return false;
    }
}
