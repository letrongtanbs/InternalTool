package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSettingUpdatePasswordReqDto {

    @NotBlank(message = "old password must not be blank!!")
    @Size(min = 8, max = 20, message = "size of old password must between 8 and 20!!")
    private String oldPassword;

    @NotBlank(message = "new password must not be blank!!")
    @Size(min = 8, max = 20, message = "size of new password must between 8 and 20!!")
    private String newPassword;

}
