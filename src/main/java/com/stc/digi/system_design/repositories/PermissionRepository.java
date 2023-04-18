package com.stc.digi.system_design.repositories;

import com.stc.digi.system_design.entities.PermissionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionTable,Long> {

    Optional<PermissionTable>  findByUserEmail(String userEmail);
}
