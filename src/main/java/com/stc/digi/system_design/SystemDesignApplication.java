package com.stc.digi.system_design;

import com.stc.digi.system_design.entities.PermissionGroupTable;
import com.stc.digi.system_design.entities.PermissionTable;
import com.stc.digi.system_design.repositories.PermissionGroupRepository;
import com.stc.digi.system_design.repositories.PermissionRepository;
import com.stc.digi.system_design.util.PermissionLevel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemDesignApplication  {



    public static void main(String[] args) {
        SpringApplication.run(SystemDesignApplication.class, args);
    }


}
