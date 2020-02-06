package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserSettingReqDto {

    private String departmentId;

    private String teamId;

    private String title;

    private String address;

    private String phone;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String languageCode;

    @NotNull
    private int status;

    private String avatar;

}
