package com.stc.digi.system_design.repositories;

import com.stc.digi.system_design.entities.FileTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileTable,Long> {
}
