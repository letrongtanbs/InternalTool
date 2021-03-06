package com.tvj.internaltool.dto.res;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingResDto {

    private String username;

    private String firstName;

    private String lastName;

    private String email;
    
    private String titleName;

    private String departmentId;

    private String departmentName;

    private String teamId;

    private String teamName;

    private String address;

    private String phone;

    private String countryId;

    private String countryName;

    private String languageId;

    private String languageName;

    private LocalDateTime updatedDate;

    private String statusId;
    
    private String statusName;

    private String avatar;
    
}
