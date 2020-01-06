package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserLoginReqDto {

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    private String password;

}
