package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class RecoverPasswordReqDto {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8)
    private String newPassword;

}
