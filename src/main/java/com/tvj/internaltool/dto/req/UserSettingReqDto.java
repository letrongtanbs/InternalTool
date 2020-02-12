package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserSettingReqDto {

    private String teamId;

    private String title;

    private String address;

    private String phone;

    private String countryId;

    @NotBlank
    private String languageId;

    @NotNull
    private int status;

    private String avatar;

}
