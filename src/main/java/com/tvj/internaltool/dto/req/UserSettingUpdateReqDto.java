package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tvj.internaltool.validation.UserStatusConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSettingUpdateReqDto {

    @Size(min = 1, max = 40)
    private String teamId;

    @Size(max = 400)
    private String address;

    @Size(min = 8, max = 20)
    private String phone;

    @Size(min = 1, max = 40)
    private String countryId;

    @NotBlank
    @Size(min = 1, max = 40)
    private String languageId;

    @NotBlank
    @UserStatusConstraint
    private String statusId;

}
