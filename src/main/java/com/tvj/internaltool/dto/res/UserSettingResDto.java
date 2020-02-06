package com.tvj.internaltool.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingResDto {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String departmentId;

    private String departmentName;

    private String teamId;

    private String teamName;

    private String title;

    private String address;

    private String phone;

    private String countryId;

    private String countryName;

    private String languageId;

    private String languageName;

    private LocalDateTime updatedDate;

    private int status;
}
