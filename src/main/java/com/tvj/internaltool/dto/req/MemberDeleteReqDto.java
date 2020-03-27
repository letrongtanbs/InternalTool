package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDeleteReqDto {
    
    @NotBlank
    @Size(max = 20)
    private String username;
    
}
