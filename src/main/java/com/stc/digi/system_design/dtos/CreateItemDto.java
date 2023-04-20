package com.stc.digi.system_design.dtos;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemDto {
    @NotNull(message = "Space name is mandatory")
    private String name;

    @Pattern(regexp = "SPACE|Folder|FILE", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String itemType;

    // optional
    private Long itemParentId;
    // optional
    private CreateGroupDto createGroupDto;
    // optional login user
    private String loginUser;

    // optional
    @Lob
    private byte[] binaryFile;


}
