package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tvj.internaltool.validation.UserStatusConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSettingUpdateReqDto {

    @Size(min = 1, max = 40, message = "size of team id must between 1 and 40!!")
    private String teamId;

    @Size(max = 400, message = "maximum size of address is 400!!")
    private String address;

    @Size(min = 8, max = 20, message = "size of phone must between 8 and 20!!")
    private String phone;

    @Size(min = 1, max = 40, message = "size of country id must between 1 and 40!!")
    private String countryId;

    @NotBlank(message = "language id must not be blank!!")
    @Size(min = 1, max = 40, message = "size of language id must between 1 and 40!!")
    private String languageId;

    @NotBlank(message = "status id must not be blank!!")
    @UserStatusConstraint
    private String statusId;

}
