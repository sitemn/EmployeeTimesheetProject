package com.site.employeetimesheetproject.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * ClassName: SignupRequest
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String employeeName;

    @NotBlank
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    private String password;

}
