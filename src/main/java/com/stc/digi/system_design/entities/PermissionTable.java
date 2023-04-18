package com.stc.digi.system_design.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "permission_level")
    private String permissionLevel;


    @ManyToOne
    @JoinColumn(name="group_id")
    private PermissionGroupTable permissionGroupTable;
}
