package com.stc.digi.system_design.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDto {


    @NotNull(message = "Group name is mandatory")
    private String groupName;

    private List<CreatePermissionDto> createPermissionDtoList;

}
