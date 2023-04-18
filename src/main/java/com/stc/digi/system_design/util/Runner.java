package com.stc.digi.system_design.util;

import com.stc.digi.system_design.entities.PermissionGroupTable;
import com.stc.digi.system_design.entities.PermissionTable;
import com.stc.digi.system_design.repositories.PermissionGroupRepository;
import com.stc.digi.system_design.repositories.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
/*public class Runner implements CommandLineRunner {

    private final PermissionGroupRepository permissionGroupRepository;

    private final PermissionRepository permissionRepository;

    public Runner(PermissionGroupRepository permissionGroupRepository , PermissionRepository permissionRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
        this.permissionRepository = permissionRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        //check if db is empty
        if (!permissionGroupRepository.findById(1l).isPresent())
        {
            // populate permission group table with (1,admin)
            PermissionGroupTable permissionGroupTable = new PermissionGroupTable();
            permissionGroupTable.setGroup_name("Admin");
            permissionGroupRepository.save(permissionGroupTable);


            //populate permission table with (1,user1,VIEW,1)
            permissionRepository.save(PermissionTable.builder()
                    .user_email("user1")
                    .permission_level(PermissionLevel.VIEW.name())
                    .permissionGroupTable(permissionGroupTable).build());

            //populate permission table with (2,user2,EDIT,1)
            permissionRepository.save(PermissionTable.builder()
                    .user_email("user2")
                    .permission_level(PermissionLevel.EDIT.name())
                    .permissionGroupTable(permissionGroupTable).build());

        }

    }
}*/
