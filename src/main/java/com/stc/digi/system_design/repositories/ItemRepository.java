package com.stc.digi.system_design.repositories;

import com.stc.digi.system_design.entities.ItemTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemTable,Long> {
}
