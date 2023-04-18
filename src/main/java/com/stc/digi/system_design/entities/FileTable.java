package com.stc.digi.system_design.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private byte[] binaryFile;

    @ManyToOne
    @JoinColumn(name="item_id")
    private ItemTable ItemTable;
}
