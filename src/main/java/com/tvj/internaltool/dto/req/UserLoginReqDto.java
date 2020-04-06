package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginReqDto {

    @NotBlank(message = "username must not be blank!!")
    @Size(max = 20, message = "maximum size of username id is 20!!")
    private String username;

    @NotBlank(message = "password must not be blank!!")
    @Size(min = 8, max = 20, message = "size of password must between 8 and 20!!")
    private String password;

}
