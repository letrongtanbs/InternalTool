package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecoverPasswordReqDto {

    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8, max = 20)
    private String newPassword;

}
