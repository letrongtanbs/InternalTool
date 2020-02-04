package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class RecoverPasswordReqDto {

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;

}
