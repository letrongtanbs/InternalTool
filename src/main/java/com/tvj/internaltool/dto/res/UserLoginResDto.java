package com.tvj.internaltool.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResDto {

    private String token;

    private String firstName;

    private String lastName;

    private String roleName;

    private boolean isFirstTimeLogin;

    private String avatar;

}
