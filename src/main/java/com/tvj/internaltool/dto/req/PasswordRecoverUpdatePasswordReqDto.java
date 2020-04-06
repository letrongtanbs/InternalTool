package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordRecoverUpdatePasswordReqDto {

    @NotBlank(message = "token must not be blank!!")
    private String token;

    @NotBlank(message = "new password must not be blank!!")
    @Size(min = 8, max = 20, message = "size of new password must between 8 and 20!!")
    private String newPassword;

}
