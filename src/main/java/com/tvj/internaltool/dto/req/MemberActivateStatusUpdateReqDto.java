package com.tvj.internaltool.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberActivateStatusUpdateReqDto {
    
    @NotBlank
    @Size(max = 20)
    private String username;
    
    private boolean isActivated;
    
}
