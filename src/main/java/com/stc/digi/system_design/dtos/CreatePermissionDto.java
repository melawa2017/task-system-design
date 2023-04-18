package com.stc.digi.system_design.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionDto {

    @Pattern(regexp = "VIEW|EDIT", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String permissionLevel;

    private String userEmail;




}
