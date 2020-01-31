package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ForgotPasswordReqDto {

    @NotBlank
    private String username;

    private String captchaResponse;
}
