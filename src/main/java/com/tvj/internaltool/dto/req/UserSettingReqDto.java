package com.tvj.internaltool.dto.req;

import com.tvj.internaltool.validation.UserStatusConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserSettingReqDto {

    private String teamId;

    private String title;

    private String address;

    @Size(min = 8, max = 20)
    private String phone;

    private String countryId;

    @NotBlank
    private String languageId;

    @NotNull
    @UserStatusConstraint
    private int status;

}
