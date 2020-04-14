package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDeleteReqDto {
    
    @NotBlank(message = "username must not be blank!!")
    @Size(max = 20, message = "maximum size of username is 20!!")
    private String username;
    
}
