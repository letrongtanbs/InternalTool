package com.tvj.internaltool.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResDto {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String title;

    private boolean isActivated;

}
