package com.tvj.internaltool.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ForgotPasswordReqDto {

    @NotBlank
    @Size(max = 20)
    private String username;

}
