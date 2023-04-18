package com.stc.digi.system_design.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String name;

    @ManyToOne
    @JoinColumn(name="group_id")
    private PermissionGroupTable permissionGroupTable;

    @ManyToOne
    private ItemTable parentItem;

    @OneToMany(mappedBy="parentItem")
    private List<ItemTable>  itemTableList;
}
