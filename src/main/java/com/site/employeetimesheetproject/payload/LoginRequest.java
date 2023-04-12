package com.site.employeetimesheetproject.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    private String employeeName;

    @NotBlank
    private String password;
}
