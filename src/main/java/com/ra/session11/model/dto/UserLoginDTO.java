package com.ra.session11.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginDTO {
    @NotBlank(message = "username can not blank")
    private String username;
    @NotBlank(message = "password can not blank")
    private String password;
}
