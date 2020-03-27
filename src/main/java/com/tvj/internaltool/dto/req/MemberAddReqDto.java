package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tvj.internaltool.validation.UserEmailConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberAddReqDto {
    
    @NotBlank
    @Size(max = 20)
    private String username;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    private String titleId;
    
    @NotBlank
    @UserEmailConstraint
    private String email;
    
    @NotBlank
    private String password;

}
