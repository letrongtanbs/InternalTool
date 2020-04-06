package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.tvj.internaltool.validation.UserEmailConstraint;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Validated
public class MemberAddReqDto {
    
    @NotBlank(message = "username must not be blank!!")
    @Size(max = 20, message = "maximum size of username id is 20!!")
    private String username;
    
    @NotBlank(message = "first name must not be blank!!")
    private String firstName;
    
    @NotBlank(message = "last name must not be blank!!")
    private String lastName;
    
    @NotBlank(message = "title id must not be blank!!")
    @Size(max = 40, message = "maximum size of title id is 40!!")
    private String titleId;
    
    @NotBlank(message = "email must not be blank!!")
    @UserEmailConstraint
    private String email;
    
    @NotBlank(message = "password must not be blank!!")
    private String password;

}
