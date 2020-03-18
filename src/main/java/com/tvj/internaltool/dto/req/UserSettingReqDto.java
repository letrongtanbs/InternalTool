package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tvj.internaltool.validation.UserStatusConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSettingReqDto {

    private String teamId;

    private String address;

    @Size(min = 8, max = 20)
    private String phone;

    private String countryId;

    @NotBlank
    private String languageId;

    @NotBlank
    @UserStatusConstraint
    private String statusId;

}
